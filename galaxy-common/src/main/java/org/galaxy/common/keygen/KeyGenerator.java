package org.galaxy.common.keygen;

import java.util.Properties;

public interface KeyGenerator {

    /**
     * Generate key.
     *
     * @return generated key
     */
    Comparable<?> generateKey();

    /**
     * Get algorithm type.
     *
     * @return type
     */
    String getType();

    /**
     * Get properties.
     *
     * @return properties of algorithm
     */
    Properties getProperties();

    /**
     * Set properties.
     *
     * @param properties properties of algorithm
     */
    void setProperties(Properties properties);
}
