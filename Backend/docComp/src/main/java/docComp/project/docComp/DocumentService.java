package docComp.project.docComp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    public Document saveDocument(String name, String content) {
        Document document = new Document();
        document.setName(name);
        document.setContent(content);
        document.setUploadTime(LocalDateTime.now());
        return documentRepository.save(document);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }
}
