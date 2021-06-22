package com.example.search.enums;

public enum EOperator {

    /**
     * Tests whether two expressions are equal
     */
    EQUALS,

    /**
     * Tests whether the expression matches a given pattern
     */
    LIKE,

    /**
     * Tests whether the expression do not matches a given pattern
     */
    NOT_LIKE,

    /**
     * Tests whether two expressions are not equal
     */
    NOT_EQ,

    /**
     * Tests whether the first numeric expression is greater than the second numeric expression
     */
    GREATER_THAN,

    /**
     * Tests whether the first numeric expression is greater than or equal to the second numeric expression
     */
    GREATER_THAN_EQUAL,

    /**
     * Tests whether the first numeric expression is less than the second numeric expression
     */
    LESS_THAN,

    /**
     * Tests whether the first numeric expression is less than or equal to the second numeric expression
     */
    LESS_THAN_EQUAL,

    /**
     * Tests whether the first expression is between the second and third expression in value
     */
    BETWEEN,

    /**
     * Tests whether an expression is null
     */
    iS_NULL,

    /**
     * /Tests whether an expression is not null
     */
    iS_NOT_NULL,

    /**
     * Tests whether an expression is within a list of values
     */
    IN
}
