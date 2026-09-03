package com.openlattice.chronicle;

import static com.openlattice.chronicle.ChronicleTestUtils.JSON_MIME_TYPE;
import static com.openlattice.chronicle.ChronicleTestUtils.PLAIN_TEXT_MIME_TYPE;
import static com.openlattice.chronicle.ChronicleTestUtils.createHttpClient;
import static com.openlattice.chronicle.ChronicleTestUtils.createRetrofitAdapter;
import static com.openlattice.chronicle.ChronicleTestUtils.quote;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.HttpHeaders;
import com.openlattice.chronicle.sources.AndroidDevice;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import okhttp3.HttpUrl;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public class ChronicleStudyApiTests {
    private static final Logger logger = LoggerFactory.getLogger( ChronicleStudyApiTests.class );
    private static ChronicleStudyApi chronicleStudyApi;

    @Test
    public void testEnrollDevice() {
        AndroidDevice datasource = new AndroidDevice( RandomStringUtils.random( 10 ),
                RandomStringUtils.random( 10 ),
                RandomStringUtils.random( 10 ),
                RandomStringUtils.random( 10 ),
                RandomStringUtils.random( 10 ),
                RandomStringUtils.random( 10 ),
                RandomStringUtils.random( 10 ),
                RandomStringUtils.random( 10 ),
                ImmutableMap.of( RandomStringUtils.random( 10 ),
                        10,
                        RandomStringUtils.random( 10 ),
                        RandomStringUtils.random( 10 ) ), "" );
        UUID id = chronicleStudyApi
                .enrollSource( UUID.randomUUID(),
                        RandomStringUtils.randomAlphanumeric( 16 ),
                        RandomStringUtils.randomAlphanumeric( 10 ),
                        Optional.of( datasource ) );
        Assert.assertNotNull( "Returned id cannot be null.", id );
    }

    @BeforeClass
    public static void createChronicleApi() {
        chronicleStudyApi = createRetrofitAdapter( createHttpClient( ChronicleStudyApiTests::handleRequests ) )
                .create( ChronicleStudyApi.class );
    }

    public static Response handleRequests( Chain chain ) throws IOException {
        final Request request = chain.request();
        final String method = request.method();
        final HttpUrl url = request.url();
        final List<String> pathSegments = url.pathSegments();

        Assert.assertTrue( ChronicleStudyApi.SERVICE.endsWith( pathSegments.get( 0 ) ) );
        Assert.assertTrue( ChronicleStudyApi.CONTROLLER.endsWith( pathSegments.get( 1 ) ) );

        Response.Builder responseBuilder = new Response.Builder()
                .code( 200 )
                .message( "TEST" )
                .protocol( Protocol.HTTP_1_1 )
                .request( request );

        switch ( method ) {
            case "POST":
                return responseBuilder
                        .header( HttpHeaders.CONTENT_TYPE, JSON_MIME_TYPE )
                        .body( ResponseBody
                                .create( MediaType.parse( JSON_MIME_TYPE ), quote( UUID.randomUUID().toString() ) ) )
                        .build();
            case "GET":
                return responseBuilder
                        .header( HttpHeaders.CONTENT_TYPE, PLAIN_TEXT_MIME_TYPE )
                        .body( ResponseBody
                                .create( MediaType.parse( PLAIN_TEXT_MIME_TYPE ), quote( Boolean.TRUE.toString() ) ) )
                        .build();
            default:
                String errMsg = "Unsupported method: " + method;
                logger.error( errMsg );
                throw new UnsupportedOperationException( errMsg );

        }
    }

}
