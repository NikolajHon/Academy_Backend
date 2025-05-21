package sk.posam.fsa.discussion.jpa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import sk.posam.fsa.discussion.CodeExecutionRequest;
import sk.posam.fsa.discussion.CodeExecutionResult;
import sk.posam.fsa.discussion.service.CodeExecutionPort;

import java.util.Map;

@Repository
public class JdoodleExecutionRestAdapter implements CodeExecutionPort {

    private final RestTemplate rest;
    private final String apiUrl;
    private final String clientId;
    private final String clientSecret;

    public JdoodleExecutionRestAdapter(
            RestTemplate rest,
            @Value("${jdoodle.api-url}")      String apiUrl,
            @Value("${jdoodle.client-id}")    String clientId,
            @Value("${jdoodle.client-secret}")String clientSecret) {

        this.rest         = rest;
        this.apiUrl       = apiUrl;
        this.clientId     = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public CodeExecutionResult execute(CodeExecutionRequest req) {

        System.out.println("\n→ JDoodle call  | language=" + req.language()
                + " version=" + req.versionIndex()
                + "  (" + req.source().length() + " bytes source)");

        Map<String, String> body = Map.of(
                "clientId",      clientId,
                "clientSecret",  clientSecret,
                "script",        req.source(),
                "language",      req.language(),
                "versionIndex",  req.versionIndex()
        );

        var r = rest.postForObject(apiUrl, body, JdoodleResponse.class);

        System.out.println("← JDoodle reply | status=" + r.statusCode());
        System.out.println("stdout:\n" + r.output());
        System.out.println("stderr:\n" + r.stderr());

        return new CodeExecutionResult(r.statusCode(), r.output(), r.stderr());
    }


    private record JdoodleResponse(int statusCode, String output, String stderr) {}
}
