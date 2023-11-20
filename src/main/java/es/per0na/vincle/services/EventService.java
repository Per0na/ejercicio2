package es.per0na.vincle.services;

import es.per0na.vincle.data.Event;
import es.per0na.vincle.data.Item;
import es.per0na.vincle.data.types.ItemState;
import es.per0na.vincle.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Item item, ItemState state, String clientName) {
        Event newEvent = new Event();
        newEvent.setState(state);
        newEvent.setItem(item);
        newEvent.setClientName(clientName);
        return eventRepository.save(newEvent);
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public Event updateEvent(Long id, Event newEventData) {
        return eventRepository.findById(id).map(event -> {
            event.setClientName(newEventData.getClientName());
            event.setItem(newEventData.getItem());
            event.setTimestamp(newEventData.getTimestamp());
            event.setState(newEventData.getState());
            return eventRepository.save(event);
        }).orElseThrow(() -> new RuntimeException("Event not found with id " + id));
    }
}
