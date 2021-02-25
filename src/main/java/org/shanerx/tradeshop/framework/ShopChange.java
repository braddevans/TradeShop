package org.shanerx.tradeshop.framework;

/**
 * This enum's entries represent the possible changes the {@link org.shanerx.tradeshop.framework.events.PlayerShopChangeEvent} is fired upon.
 */
public enum ShopChange {

    /**
     * Add manager shop change.
     */
    ADD_MANAGER,
    /**
     * Add member shop change.
     */
    ADD_MEMBER,
    /**
     * Add product shop change.
     */
    ADD_PRODUCT,
    /**
     * Add cost shop change.
     */
    ADD_COST,
    /**
     * Remove user shop change.
     */
    REMOVE_USER,
    /**
     * Remove product shop change.
     */
    REMOVE_PRODUCT,
    /**
     * Remove cost shop change.
     */
    REMOVE_COST,
    /**
     * Set product shop change.
     */
    SET_PRODUCT,
    /**
     * Set cost shop change.
     */
    SET_COST
}
