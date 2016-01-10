package com.google.TVShow.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "castingEpisodeApi",
        version = "v1",
        resource = "castingEpisode",
        namespace = @ApiNamespace(
                ownerDomain = "backend.TVShow.google.com",
                ownerName = "backend.TVShow.google.com",
                packagePath = ""
        )
)
public class CastingEpisodeEndpoint {

    private static final Logger logger = Logger.getLogger(CastingEpisodeEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(CastingEpisode.class);
    }

    /**
     * Returns the {@link CastingEpisode} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code CastingEpisode} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "castingEpisode/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CastingEpisode get(@Named("id") int id) throws NotFoundException {
        logger.info("Getting CastingEpisode with ID: " + id);
        CastingEpisode castingEpisode = ofy().load().type(CastingEpisode.class).id(id).now();
        if (castingEpisode == null) {
            throw new NotFoundException("Could not find CastingEpisode with ID: " + id);
        }
        return castingEpisode;
    }

    /**
     * Inserts a new {@code CastingEpisode}.
     */
    @ApiMethod(
            name = "insert",
            path = "castingEpisode",
            httpMethod = ApiMethod.HttpMethod.POST)
    public CastingEpisode insert(CastingEpisode castingEpisode) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that castingEpisode.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(castingEpisode).now();
        logger.info("Created CastingEpisode with ID: " + castingEpisode.getId());

        return ofy().load().entity(castingEpisode).now();
    }

    /**
     * Updates an existing {@code CastingEpisode}.
     *
     * @param id             the ID of the entity to be updated
     * @param castingEpisode the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code CastingEpisode}
     */
    @ApiMethod(
            name = "update",
            path = "castingEpisode/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public CastingEpisode update(@Named("id") int id, CastingEpisode castingEpisode) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(castingEpisode).now();
        logger.info("Updated CastingEpisode: " + castingEpisode);
        return ofy().load().entity(castingEpisode).now();
    }

    /**
     * Deletes the specified {@code CastingEpisode}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code CastingEpisode}
     */
    @ApiMethod(
            name = "remove",
            path = "castingEpisode/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") int id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(CastingEpisode.class).id(id).now();
        logger.info("Deleted CastingEpisode with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "castingEpisode",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<CastingEpisode> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<CastingEpisode> query = ofy().load().type(CastingEpisode.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<CastingEpisode> queryIterator = query.iterator();
        List<CastingEpisode> castingEpisodeList = new ArrayList<CastingEpisode>(limit);
        while (queryIterator.hasNext()) {
            castingEpisodeList.add(queryIterator.next());
        }
        return CollectionResponse.<CastingEpisode>builder().setItems(castingEpisodeList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(int id) throws NotFoundException {
        try {
            ofy().load().type(CastingEpisode.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find CastingEpisode with ID: " + id);
        }
    }
}