package com.niyiment.fitness.external;

public interface ExternalApiClient<T, ID> {
    /**
     * Create a new resource on the external API
     * @param object the resource to create
     * @return the create resource on the external API
     */
    T create(T object);

    /**
     * Update an existing resource on the external API
     * @param object the resource to update
     * @return the updated resource on the external API
     */
    T update(ID d, T object);

    /**
     * Reads a resource based on identifier
     * @param id the resource identifier
     * @return the resource or nulll/exception if not fount
     */
    T read(ID id);

    /**
     * Read all resources from the external API
     * @return all resources or an empty list/exception if none found
     */
    T readAll();

    /**
     * Delete a resource from the external API
     * @param id the resource identifier
     */
    void delete(ID id);
}

