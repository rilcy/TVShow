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
        name = "showApi",
        version = "v1",
        resource = "show",
        namespace = @ApiNamespace(
                ownerDomain = "backend.TVShow.google.com",
                ownerName = "backend.TVShow.google.com",
                packagePath = ""
        )
)
public class ShowEndpoint {

    private static final Logger logger = Logger.getLogger(ShowEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Show.class);
    }

    /**
     * Returns the {@link Show} with the corresponding ID.
     *
     * @param showId the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Show} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "show/{showId}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Show get(@Named("showId") int showId) throws NotFoundException {
        logger.info("Getting Show with ID: " + showId);
        Show show = ofy().load().type(Show.class).id(showId).now();
        if (show == null) {
            throw new NotFoundException("Could not find Show with ID: " + showId);
        }
        return show;
    }

    /**
     * Inserts a new {@code Show}.
     */
    @ApiMethod(
            name = "insert",
            path = "show",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Show insert(Show show) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that show.showId has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(show).now();
        logger.info("Created Show with ID: " + show.getShowId());

        return ofy().load().entity(show).now();
    }

    /**
     * Updates an existing {@code Show}.
     *
     * @param showId the ID of the entity to be updated
     * @param show   the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code showId} does not correspond to an existing
     *                           {@code Show}
     */
    @ApiMethod(
            name = "update",
            path = "show/{showId}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Show update(@Named("showId") int showId, Show show) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(showId);
        ofy().save().entity(show).now();
        logger.info("Updated Show: " + show);
        return ofy().load().entity(show).now();
    }

    /**
     * Deletes the specified {@code Show}.
     *
     * @param showId the ID of the entity to delete
     * @throws NotFoundException if the {@code showId} does not correspond to an existing
     *                           {@code Show}
     */
    @ApiMethod(
            name = "remove",
            path = "show/{showId}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("showId") int showId) throws NotFoundException {
        checkExists(showId);
        ofy().delete().type(Show.class).id(showId).now();
        logger.info("Deleted Show with ID: " + showId);
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
            path = "show",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Show> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Show> query = ofy().load().type(Show.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Show> queryIterator = query.iterator();
        List<Show> showList = new ArrayList<Show>(limit);
        while (queryIterator.hasNext()) {
            showList.add(queryIterator.next());
        }
        return CollectionResponse.<Show>builder().setItems(showList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(int showId) throws NotFoundException {
        try {
            ofy().load().type(Show.class).id(showId).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Show with ID: " + showId);
        }
    }
}