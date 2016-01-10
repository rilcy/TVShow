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
        name = "episodeApi",
        version = "v1",
        resource = "episode",
        namespace = @ApiNamespace(
                ownerDomain = "backend.TVShow.google.com",
                ownerName = "backend.TVShow.google.com",
                packagePath = ""
        )
)
public class EpisodeEndpoint {

    private static final Logger logger = Logger.getLogger(EpisodeEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Episode.class);
    }

    /**
     * Returns the {@link Episode} with the corresponding ID.
     *
     * @param episodeID the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Episode} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "episode/{episodeID}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Episode get(@Named("episodeID") int episodeID) throws NotFoundException {
        logger.info("Getting Episode with ID: " + episodeID);
        Episode episode = ofy().load().type(Episode.class).id(episodeID).now();
        if (episode == null) {
            throw new NotFoundException("Could not find Episode with ID: " + episodeID);
        }
        return episode;
    }

    /**
     * Inserts a new {@code Episode}.
     */
    @ApiMethod(
            name = "insert",
            path = "episode",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Episode insert(Episode episode) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that episode.episodeID has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(episode).now();
        logger.info("Created Episode with ID: " + episode.getEpisodeID());

        return ofy().load().entity(episode).now();
    }

    /**
     * Updates an existing {@code Episode}.
     *
     * @param episodeID the ID of the entity to be updated
     * @param episode   the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code episodeID} does not correspond to an existing
     *                           {@code Episode}
     */
    @ApiMethod(
            name = "update",
            path = "episode/{episodeID}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Episode update(@Named("episodeID") int episodeID, Episode episode) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(episodeID);
        ofy().save().entity(episode).now();
        logger.info("Updated Episode: " + episode);
        return ofy().load().entity(episode).now();
    }

    /**
     * Deletes the specified {@code Episode}.
     *
     * @param episodeID the ID of the entity to delete
     * @throws NotFoundException if the {@code episodeID} does not correspond to an existing
     *                           {@code Episode}
     */
    @ApiMethod(
            name = "remove",
            path = "episode/{episodeID}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("episodeID") int episodeID) throws NotFoundException {
        checkExists(episodeID);
        ofy().delete().type(Episode.class).id(episodeID).now();
        logger.info("Deleted Episode with ID: " + episodeID);
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
            path = "episode",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Episode> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Episode> query = ofy().load().type(Episode.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Episode> queryIterator = query.iterator();
        List<Episode> episodeList = new ArrayList<Episode>(limit);
        while (queryIterator.hasNext()) {
            episodeList.add(queryIterator.next());
        }
        return CollectionResponse.<Episode>builder().setItems(episodeList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(int episodeID) throws NotFoundException {
        try {
            ofy().load().type(Episode.class).id(episodeID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Episode with ID: " + episodeID);
        }
    }
}