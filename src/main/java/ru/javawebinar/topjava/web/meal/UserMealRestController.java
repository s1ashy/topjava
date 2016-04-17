package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping(UserMealRestController.REST_URL)
public class UserMealRestController extends AbstractUserMealController {
    static final String REST_URL = "/rest/meals";
    private static final Logger LOG = LoggerFactory.getLogger(UserMealRestController.class);

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMeal> getWithAck(@PathVariable int id) {
        try {
            return new ResponseEntity<>(super.get(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UserMeal> deleteWithAck(@PathVariable int id) {
        try {
            super.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserMeal> updateWithAck(@RequestBody UserMeal meal,
                                                  @PathVariable int id) {
        try {
            super.update(meal, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMeal> createWithLocation(@RequestBody UserMeal meal) {
        UserMeal created = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriOfNewResource);

        return new ResponseEntity<>(created, httpHeaders, HttpStatus.CREATED);
    }

    //@RequestMapping(value = "/between", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getBetween(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end) {
        LocalDate startDate = start == null ? null : LocalDate.from(start);
        LocalTime startTime = start == null ? null : LocalTime.from(start);
        LocalDate endDate = end == null ? null : LocalDate.from(end);
        LocalTime endTime = end == null ? null : LocalTime.from(end);

        LOG.debug("startDate: {}", startDate);
        LOG.debug("endDate: {}", endDate);
        LOG.debug("startTime: {}", startTime);
        LOG.debug("endTime: {}", endTime);

        return super.getBetween(startDate, startTime, endDate, endTime);
    }

    @RequestMapping(value = "/between", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getBetweenHW7Optional(
            @RequestParam(value = "from", required = false) String startParam,
            @RequestParam(value = "to", required = false) String endParam) {

        LocalDateTime start = LocalDateTime.parse(startParam, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime end = LocalDateTime.parse(endParam, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDate startDate = start == null ? null : LocalDate.from(start);
        LocalTime startTime = start == null ? null : LocalTime.from(start);
        LocalDate endDate = end == null ? null : LocalDate.from(end);
        LocalTime endTime = end == null ? null : LocalTime.from(end);

        LOG.debug("startDate: {}", startDate);
        LOG.debug("endDate: {}", endDate);
        LOG.debug("startTime: {}", startTime);
        LOG.debug("endTime: {}", endTime);

        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}