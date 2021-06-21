package com.example.search.enums;

public enum EOperator {
    GREATER_THAN,
    LESS_THAN,

    /**
     * Tests whether two expressions are equal
     */
    EQUALS,

    /**
     * Tests whether the expression matches a given pattern
     */
    LIKE,

    /**
     * Tests whether two expressions are not equal
     */
    NOT_EQ,

    /**
     * Tests whether the first numeric expression is greater than the second numeric expression
     */
    GT,

    /**
     * Tests whether the first numeric expression is greater than or equal to the second numeric expression
     */
    GE,

    /**
     * Tests whether the first numeric expression is less than the second numeric expression
     */
    LT,

    /**
     * Tests whether the first numeric expression is less than or equal to the second numeric expression
     */
    LE,

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
