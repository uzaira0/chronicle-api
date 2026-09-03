package com.openlattice.chronicle;

import static com.openlattice.chronicle.ChronicleTestUtils.JSON_MIME_TYPE;
import static com.openlattice.chronicle.ChronicleTestUtils.createHttpClient;
import static com.openlattice.chronicle.ChronicleTestUtils.createRetrofitAdapter;
import static com.openlattice.chronicle.ChronicleTestUtils.mapper;
import static com.openlattice.chronicle.ChronicleTestUtils.readBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.collect.SetMultimap;
import com.google.common.net.HttpHeaders;
import com.openlattice.chronicle.util.RetrofitBuilders;
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
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public class ChronicleApiTests {
    private static final Logger logger = LoggerFactory.getLogger( ChronicleApiTests.class );

    private static ChronicleApi chronicleApi;
    static {
        RetrofitBuilders.mapper.registerModule( new GuavaModule() );
    }

    @Test
    public void testSubmitData() {
        List<SetMultimap<UUID, Object>> data = ChronicleTestUtils.mockData( RandomUtils.nextInt( 10, 64 ) );
        final int uploadedCount = chronicleApi.upload(
                UUID.randomUUID(),
                RandomStringUtils.randomAlphanumeric( 16 ),
                RandomStringUtils.randomAlphanumeric( 16 ),
                data );
        Assert.assertEquals( "Uploaded count should match number of items uploaded.", data.size(), uploadedCount );

    }

    @BeforeClass
    public static void createChronicleApi() {
        chronicleApi = createRetrofitAdapter( createHttpClient( ChronicleApiTests::handleRequests ) )
                .create( ChronicleApi.class );
    }

    public static Response handleRequests( Chain chain ) throws IOException {
        final Request request = chain.request();
        final String method = request.method();
        final HttpUrl url = request.url();
        final List<String> pathSegments = url.pathSegments();

        Assert.assertTrue( ChronicleApi.SERVICE.endsWith( pathSegments.get( 0 ) ) );
        Assert.assertTrue( ChronicleApi.CONTROLLER.endsWith( pathSegments.get( 1 ) ) );

        Response.Builder responseBuilder = new Response.Builder()
                .code( 200 )
                .message( "TEST" )
                .protocol( Protocol.HTTP_1_1 )
                .request( request );

        List<SetMultimap<UUID, Object>> data = ChronicleTestUtils.mapper
                .readValue( readBody( request.body() ), new TypeReference<List<SetMultimap<UUID, Object>>>() {
                } );
        logger.debug( "Data looks like: {}", data );
        switch ( method ) {
            case "POST":
                return responseBuilder
                        .header( HttpHeaders.CONTENT_TYPE, JSON_MIME_TYPE )
                        .body( ResponseBody
                                .create( MediaType.parse( JSON_MIME_TYPE ), mapper.writeValueAsString( data.size() ) ) )
                        .build();
            default:
                String errMsg = "Unsupported method: " + method;
                logger.error( errMsg );
                throw new UnsupportedOperationException( errMsg );

        }

    }
}
