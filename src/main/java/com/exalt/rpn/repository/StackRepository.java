package com.exalt.rpn.repository;

import com.exalt.rpn.repository.model.Stack;

import java.util.Collection;
import java.util.Optional;

public interface StackRepository {

    Optional<Stack> findById(long id);

    void add(Stack stack);

    Collection<Stack> getStacks();

    void deleteStack(long id);

    void applyoperation(long id, String op, Collection<String> operandList);

    void setOperandList(Collection<String> operandList);
}
