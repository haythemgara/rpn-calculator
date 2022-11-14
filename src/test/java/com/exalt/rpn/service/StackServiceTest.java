package com.exalt.rpn.service;

import com.exalt.rpn.exception.OperationNotSupportedException;
import com.exalt.rpn.repository.model.Stack;
import com.exalt.rpn.repository.StackRepository;
import com.exalt.rpn.repository.cacheable.StackRepositoryCacheable;
import com.exalt.rpn.repository.cacheable.service.StackServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collection;
import java.util.Optional;

@SpringBootTest
@TestPropertySource("classpath:applicationTest.properties")
public class StackServiceTest {

    private StackServiceImpl stackService;
    @Value("${rpn.operand}")
    private Collection<String> operandList;

    private

    @BeforeEach
    void beforeEach() {
        StackRepository stackRepository = new StackRepositoryCacheable();
        stackService = new StackServiceImpl(stackRepository);
        stackService.setOperandList(operandList);
        //Given stack 1
        Stack stack1 = new Stack();
        stack1.setId(1);
        java.util.Stack<Integer> values1 = new java.util.Stack<>();
        values1.push(1);
        values1.push(2);
        stack1.setValues(values1);
        stackService.add(stack1);
        //Given stack 2
        Stack stack2 = new Stack();
        stack2.setId(2);
        java.util.Stack<Integer> values2 = new java.util.Stack<>();
        values2.push(2);
        values2.push(3);
        values2.push(4);
        values2.push(5);
        stack2.setValues(values2);
        stackService.add(stack2);
    }

    @Test
    void findById_OK() {
        //When
        Optional<Stack> stackResultNotNull = stackService.findById(1);
        //Then
        Assertions.assertTrue(stackResultNotNull.isPresent());
        Assertions.assertEquals(1, stackResultNotNull.get().getId());
        Assertions.assertEquals(2, stackResultNotNull.get().getValues().size());

        //When
        Optional<Stack> stackResultNull = stackService.findById(0);
        //Then
        Assertions.assertFalse(stackResultNull.isPresent());
    }

    @Test
    void getStacks_OK() {
        //When
        Collection<Stack> stacks = stackService.getStacks();

        //Then
        Assertions.assertEquals(2, stacks.size());
    }

    @Test
    void deleteStack_OK() {
        //When
        stackService.deleteStack(1);
        //And when
        Collection<Stack> stacks = stackService.getStacks();
        //Then
        Assertions.assertEquals(1, stacks.size());
    }

    @Test
    void addStack_OK() {
        //Given
        Stack stackToAdd = new Stack();
        stackToAdd.setId(3);
        java.util.Stack<Integer> values = new java.util.Stack<>();
        values.push(1);
        stackToAdd.setValues(values);

        //When
        stackService.add(stackToAdd);
        //And when
        Collection<Stack> stacks = stackService.getStacks();
        //Then
        Assertions.assertEquals(3, stacks.size());
    }

    @ParameterizedTest
    @CsvSource({"1, KO", "2, #", "1, %"})
    void applyOperation_KO_invalid_operand(long id, String op) {
        Assertions.assertThrows(OperationNotSupportedException.class, () -> stackService.applyoperation(id, op));
    }

    @ParameterizedTest
    @CsvSource({"10, /", "20, +", "25, -"})
    void applyOperation_KO_stack_null(long id, String op) {
        Assertions.assertThrows(OperationNotSupportedException.class, () -> stackService.applyoperation(id, op));
    }

    @Test
    void applyOperation_KO_stack_invalid() {
        stackService.findById(1).get().getValues().pop();
        Assertions.assertThrows(OperationNotSupportedException.class, () -> stackService.applyoperation(1, "/"));
    }

    @Test
    void applyOperation_OK_addition() throws Exception {
        //When
        stackService.applyoperation(1, "+");
        //And when
        Collection<Stack> stacks = stackService.getStacks();
        //Then
        Assertions.assertEquals(1, stacks.stream().findFirst().get().getValues().size());
    }
}
