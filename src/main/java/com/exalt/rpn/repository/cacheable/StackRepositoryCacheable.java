package com.exalt.rpn.repository.cacheable;

import com.exalt.rpn.exception.OperationNotSupportedException;
import com.exalt.rpn.repository.model.OperandEnum;
import com.exalt.rpn.repository.model.Stack;
import com.exalt.rpn.repository.StackRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StackRepositoryCacheable implements StackRepository {
    private final Map<Long, Stack> stacks;

    public StackRepositoryCacheable() {
        this.stacks = new ConcurrentHashMap<>();
    }

    @Override
    @Cacheable("stack")
    public Optional<Stack> findById(long id) {
        return Optional.ofNullable(stacks.get(id));
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "stack", allEntries = true)})
    public void add(Stack stack) {
        stacks.putIfAbsent(stack.getId(), stack);
    }

    @Override
    @Cacheable("stack")
    public Collection<Stack> getStacks() {
        return stacks.values();
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "stack", allEntries = true)})
    public void deleteStack(long id) {
        stacks.remove(id);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "stack", allEntries = true)})
    public void applyoperation(long id, String op, Collection<String> operandList) {
        Optional<Stack> stack = findById(id);
        if (stack.isEmpty() || !operandList.contains(op) || CollectionUtils.isEmpty(stack.get().getValues()) || stack.get().getValues().size() < 2) {
            throw new OperationNotSupportedException();
        }
        switch (Objects.requireNonNull(OperandEnum.getOperandEnumName(op))) {
            case DIVISION:
                stack.get().getValues().push(stack.get().getValues().pop() / stack.get().getValues().pop());
                break;
            case MULTIPLICATION:
                stack.get().getValues().push(stack.get().getValues().pop() * stack.get().getValues().pop());
                break;
            case ADDITION:
                stack.get().getValues().push(stack.get().getValues().pop() + stack.get().getValues().pop());
                break;
            case SUBSTRACTION:
                stack.get().getValues().push(stack.get().getValues().pop() - stack.get().getValues().pop());
                break;
        }
    }

    @Override
    public void setOperandList(Collection<String> operandList) {

    }
}
