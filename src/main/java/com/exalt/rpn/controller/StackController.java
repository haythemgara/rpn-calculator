package com.exalt.rpn.controller;

import com.exalt.rpn.repository.model.Stack;
import com.exalt.rpn.repository.cacheable.service.StackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping("/rpn")
public class StackController {

    @Autowired
    private StackService stackService;
    @Value("${rpn.operand}")
    Collection<String> ops;

    @Operation(summary = "Get a stack by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the stack",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Stack.class))}),
            @ApiResponse(responseCode = "404", description = "Stack not found", content = @Content)})
    @GetMapping("/stack/{id}")
    public Stack findById(@Parameter(description = "id of stack to be searched") @PathVariable long id) {
        if (stackService.findById(id).isPresent()) {
            return stackService.findById(id).get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stack not found.");
    }

    @Operation(summary = "List the available stacks")
    @GetMapping("/stack")
    public Collection<Stack> findStacks() {
        return stackService.getStacks();
    }

    @Operation(summary = "Create a new stack")
    @PostMapping("/stack")
    @ResponseStatus(HttpStatus.CREATED)
    public long postStack(@NotNull @Valid @RequestBody final Stack stack) {
        stackService.add(stack);
        return stack.getId();
    }

    @Operation(summary = "Push a new value to a stack")
    @PutMapping("/stack/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void postStack(@Parameter(description = "id of stack to be searched") @PathVariable long id, @NotNull @Valid @RequestParam final Integer value) {
        if (stackService.findById(id).isPresent()) {
            stackService.findById(id).get().getValues().push(value);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stack not found");
    }

    @Operation(summary = "Delete a stack")
    @DeleteMapping("/stack/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStack(@PathVariable final long id) {
        if (stackService.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stack does not exists");
        }
        stackService.deleteStack(id);
    }

    @Operation(summary = "List all the operand")
    @GetMapping("/op")
    public Collection<String> findOperands() {
        return ops;
    }

    @Operation(summary = "Apply an operation to a stack")
    @PatchMapping("/stack/{id}/operation")
    @ResponseStatus(HttpStatus.OK)
    public void patchStack(@RequestParam("op") final String op, @PathVariable("id") final long id) throws Exception {
            stackService.applyoperation(id, op);
    }
}
