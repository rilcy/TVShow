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
        name = "actorApi",
        version = "v1",
        resource = "actor",
        namespace = @ApiNamespace(
                ownerDomain = "backend.TVShow.google.com",
                ownerName = "backend.TVShow.google.com",
                packagePath = ""
        )
)
public class ActorEndpoint {

    private static final Logger logger = Logger.getLogger(ActorEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Actor.class);
    }

    /**
     * Returns the {@link Actor} with the corresponding ID.
     *
     * @param idActor the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Actor} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "actor/{idActor}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Actor get(@Named("idActor") int idActor) throws NotFoundException {
        logger.info("Getting Actor with ID: " + idActor);
        Actor actor = ofy().load().type(Actor.class).id(idActor).now();
        if (actor == null) {
            throw new NotFoundException("Could not find Actor with ID: " + idActor);
        }
        return actor;
    }

    /**
     * Inserts a new {@code Actor}.
     */
    @ApiMethod(
            name = "insert",
            path = "actor",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Actor insert(Actor actor) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that actor.idActor has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(actor).now();
        logger.info("Created Actor with ID: " + actor.getIdActor());

        return ofy().load().entity(actor).now();
    }

    /**
     * Updates an existing {@code Actor}.
     *
     * @param idActor the ID of the entity to be updated
     * @param actor   the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code idActor} does not correspond to an existing
     *                           {@code Actor}
     */
    @ApiMethod(
            name = "update",
            path = "actor/{idActor}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Actor update(@Named("idActor") int idActor, Actor actor) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(idActor);
        ofy().save().entity(actor).now();
        logger.info("Updated Actor: " + actor);
        return ofy().load().entity(actor).now();
    }

    /**
     * Deletes the specified {@code Actor}.
     *
     * @param idActor the ID of the entity to delete
     * @throws NotFoundException if the {@code idActor} does not correspond to an existing
     *                           {@code Actor}
     */
    @ApiMethod(
            name = "remove",
            path = "actor/{idActor}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("idActor") int idActor) throws NotFoundException {
        checkExists(idActor);
        ofy().delete().type(Actor.class).id(idActor).now();
        logger.info("Deleted Actor with ID: " + idActor);
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
            path = "actor",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Actor> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Actor> query = ofy().load().type(Actor.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Actor> queryIterator = query.iterator();
        List<Actor> actorList = new ArrayList<Actor>(limit);
        while (queryIterator.hasNext()) {
            actorList.add(queryIterator.next());
        }
        return CollectionResponse.<Actor>builder().setItems(actorList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(int idActor) throws NotFoundException {
        try {
            ofy().load().type(Actor.class).id(idActor).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Actor with ID: " + idActor);
        }
    }
}