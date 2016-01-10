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
        name = "seasonApi",
        version = "v1",
        resource = "season",
        namespace = @ApiNamespace(
                ownerDomain = "backend.TVShow.google.com",
                ownerName = "backend.TVShow.google.com",
                packagePath = ""
        )
)
public class SeasonEndpoint {

    private static final Logger logger = Logger.getLogger(SeasonEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Season.class);
    }

    /**
     * Returns the {@link Season} with the corresponding ID.
     *
     * @param seasonId the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Season} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "season/{seasonId}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Season get(@Named("seasonId") int seasonId) throws NotFoundException {
        logger.info("Getting Season with ID: " + seasonId);
        Season season = ofy().load().type(Season.class).id(seasonId).now();
        if (season == null) {
            throw new NotFoundException("Could not find Season with ID: " + seasonId);
        }
        return season;
    }

    /**
     * Inserts a new {@code Season}.
     */
    @ApiMethod(
            name = "insert",
            path = "season",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Season insert(Season season) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that season.seasonId has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(season).now();
        logger.info("Created Season with ID: " + season.getSeasonId());

        return ofy().load().entity(season).now();
    }

    /**
     * Updates an existing {@code Season}.
     *
     * @param seasonId the ID of the entity to be updated
     * @param season   the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code seasonId} does not correspond to an existing
     *                           {@code Season}
     */
    @ApiMethod(
            name = "update",
            path = "season/{seasonId}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Season update(@Named("seasonId") int seasonId, Season season) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(seasonId);
        ofy().save().entity(season).now();
        logger.info("Updated Season: " + season);
        return ofy().load().entity(season).now();
    }

    /**
     * Deletes the specified {@code Season}.
     *
     * @param seasonId the ID of the entity to delete
     * @throws NotFoundException if the {@code seasonId} does not correspond to an existing
     *                           {@code Season}
     */
    @ApiMethod(
            name = "remove",
            path = "season/{seasonId}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("seasonId") int seasonId) throws NotFoundException {
        checkExists(seasonId);
        ofy().delete().type(Season.class).id(seasonId).now();
        logger.info("Deleted Season with ID: " + seasonId);
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
            path = "season",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Season> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Season> query = ofy().load().type(Season.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Season> queryIterator = query.iterator();
        List<Season> seasonList = new ArrayList<Season>(limit);
        while (queryIterator.hasNext()) {
            seasonList.add(queryIterator.next());
        }
        return CollectionResponse.<Season>builder().setItems(seasonList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(int seasonId) throws NotFoundException {
        try {
            ofy().load().type(Season.class).id(seasonId).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Season with ID: " + seasonId);
        }
    }
}