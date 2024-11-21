package docComp.project.docComp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(@RequestParam String name, @RequestParam String content) {
        Document savedDocument = documentService.saveDocument(name, content);
        return ResponseEntity.ok(savedDocument);
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    // Change this method to accept a JSON object in the request body
    @PostMapping("/compare")
    public ResponseEntity<String> compareDocuments(@RequestBody CompareRequest request) {
        int similarity = calculateSimilarity(request.getContent1(), request.getContent2());
        return ResponseEntity.ok("Similarity: " + similarity + "%");
    }

    private int calculateSimilarity(String content1, String content2) {
        int maxLength = Math.max(content1.length(), content2.length());
        if (maxLength == 0) return 100; // Both contents are empty
        int editDistance = getLevenshteinDistance(content1, content2);
        return (int) ((1 - ((double) editDistance / maxLength)) * 100);
    }

    private int getLevenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] 
                               + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1),
                               Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }
}
