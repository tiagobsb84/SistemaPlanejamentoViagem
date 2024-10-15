package com.tiago.planning.link;

import com.tiago.planning.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public LinkResponse registerLink(LinkResponseLoad linkResponse, Trip trip) {
        Link newLink = new Link(linkResponse.title(), linkResponse.url(), trip);

        this.linkRepository.save(newLink);

        return new LinkResponse(newLink.getId());
    }

    public List<LinkData> getAllLinks(UUID id) {
        return this.linkRepository.findByTripId(id).stream().map(link -> new LinkData(
                link.getId(), link.getTitle(), link.getUrl()
        )).toList();
    }
}
