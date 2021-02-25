package org.shanerx.tradeshop.utils;

/**
 * The type Object holder.
 *
 * @param <Type>
 *         the type parameter
 */
public class ObjectHolder<Type> {

    private final Type obj;

    /**
     * Instantiates a new Object holder.
     *
     * @param obj
     *         the obj
     */
    public ObjectHolder(Type obj) {
        this.obj = obj;
    }

    /**
     * Gets object.
     *
     * @return the object
     */
    public Type getObject() {
        return obj;
    }
}
