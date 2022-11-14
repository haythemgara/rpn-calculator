package com.exalt.rpn.repository.cacheable.service;

import com.exalt.rpn.repository.model.Stack;
import com.exalt.rpn.repository.StackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class StackServiceImpl implements StackService {

    private final StackRepository stackRepository;

    @Value("${rpn.operand}")
    private Collection<String> operandList;

    @Autowired
    public StackServiceImpl(StackRepository stackRepository) {
        this.stackRepository = stackRepository;
    }

    public Optional<Stack> findById(long id) {
        return stackRepository.findById(id);
    }

    public void add(Stack stack) {
        stackRepository.add(stack);
    }

    public Collection<Stack> getStacks() {
        return stackRepository.getStacks();
    }

    public void deleteStack(long id) {
        stackRepository.deleteStack(id);
    }

    public void applyoperation(long id, String op) throws Exception {
        stackRepository.applyoperation(id, op, operandList);
    }

    public void setOperandList(Collection<String> operandList) {
        this.operandList = operandList;
    }
}
