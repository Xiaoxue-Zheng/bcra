package uk.ac.herc.bcra.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.herc.bcra.domain.PageIdentifier;
import uk.ac.herc.bcra.domain.PageView;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.repository.PageIdentifierRepository;
import uk.ac.herc.bcra.repository.PageViewRepository;
import uk.ac.herc.bcra.repository.UserRepository;
import uk.ac.herc.bcra.service.PageViewAuditService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PageViewAuditServiceImpl implements PageViewAuditService {

    @Autowired
    public PageViewRepository pageViewRepository;

    @Autowired
    public PageIdentifierRepository pageIdentifierRepository;

    @Autowired
    public UserRepository userRepository;

    @Override
    public void logPageView(String identifier, String username, LocalDateTime dateViewed) {
        PageIdentifier page = getPageIdentifierFromIdentifier(identifier);
        User user = getUserFromUsername(username);
        PageView pageView = new PageView()
            .pageIdentifier(page)
            .user(user)
            .date(dateViewed);
        
        pageViewRepository.save(pageView);
    }

    private PageIdentifier getPageIdentifierFromIdentifier(String identifier) {
        Optional<PageIdentifier> pageIdentifierOptional = pageIdentifierRepository.findOneByIdentifier(identifier);
        if (pageIdentifierOptional.isPresent()) {
            return pageIdentifierOptional.get();
        } else {
            PageIdentifier newPageIdentifier = new PageIdentifier()
                .identifier(identifier);
            return pageIdentifierRepository.save(newPageIdentifier);
        }
    }

    private User getUserFromUsername(String username) {
        Optional<User> userOptional = userRepository.findOneByLogin(username);
        return userOptional.orElse(null);
    }
    
}
