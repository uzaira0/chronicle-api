package com.openlattice.chronicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.openlattice.chronicle.util.RetrofitBuilders;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okio.Buffer;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import retrofit2.Retrofit;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public final class ChronicleTestUtils {
    public static final ObjectMapper mapper;//= ObjectMappers.getJsonMapper();
    public static final String JSON_MIME_TYPE       = "application/json";
    public static final String JSON_UTF8_MIME_TYPE  = JSON_MIME_TYPE + "; charset=UTF-8";
    public static final String PLAIN_TEXT_MIME_TYPE = "text/plain";
    public static final String BYTE_MIME_TYPE       = "application/octet-stream";

    private static final byte[] EMPTY_BYTES        = new byte[ 0 ];
    private static final Random r                  = new Random();
    private static final UUID   DATE_TIME_PROPERTY = UUID.randomUUID();
    private static final UUID   STRING_PROPERTY    = UUID.randomUUID();
    private static final UUID   INT_PROPERTY       = UUID.randomUUID();
    private static final UUID   DOUBLE_PROPERTY    = UUID.randomUUID();
    private static final UUID   BOOLEAN_PROPERTY   = UUID.randomUUID();
    private static final UUID   BYTES_PROPERTY     = UUID.randomUUID();

    private static final UUID[]     PROPERTY_TYPE_IDS = new UUID[] {
            STRING_PROPERTY,
            DATE_TIME_PROPERTY,
            INT_PROPERTY,
            DOUBLE_PROPERTY,
            BOOLEAN_PROPERTY,
            BYTES_PROPERTY };
    private static final Supplier[] SUPPLIERS         = new Supplier[] {
            () -> RandomStringUtils.random( 16 ),
            () -> OffsetDateTime.now().minus( r.nextInt( 720 * 24 * 60 * 60 ), ChronoUnit.SECONDS ),
            r::nextInt,
            r::nextDouble,
            r::nextBoolean,
            () -> RandomUtils.nextBytes( 1024 ),
    };

    static {
        mapper = new ObjectMapper();
        mapper.registerModule( new GuavaModule() );
        mapper.configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false );
    }

    private ChronicleTestUtils() {
    }

    public static List<SetMultimap<UUID, Object>> mockData( int count ) {
        final List<SetMultimap<UUID, Object>> mockRecords = new ArrayList<>( count );
        for ( int i = 0; i < count; ++i ) {
            final int propertyCount = RandomUtils.nextInt( 8, 32 );
            final SetMultimap<UUID, Object> mockRecord = HashMultimap.create();
            mockRecords.add( mockRecord );
            for ( int j = 0; j < propertyCount; ++j ) {
                addRandomProperty( mockRecord );
            }
        }
        return mockRecords;
    }

    public static void addRandomProperty( SetMultimap<UUID, Object> m ) {
        final int propertyTypeIndex = r.nextInt( PROPERTY_TYPE_IDS.length );

        final UUID propertyTypeId = PROPERTY_TYPE_IDS[ propertyTypeIndex ];
        final Object property = SUPPLIERS[ propertyTypeIndex ].get();
        m.put( propertyTypeId, property );
    }

    public static OkHttpClient createHttpClient( Interceptor... interceptors ) {
        OkHttpClient.Builder okhcBuilder = new OkHttpClient.Builder();
        for ( Interceptor interceptor : interceptors ) {
            okhcBuilder.addInterceptor( interceptor );
        }
        return okhcBuilder.build();
    }

    public static Retrofit createRetrofitAdapter( OkHttpClient httpClient ) {
        return RetrofitBuilders.decorateWithRhizomeFactories( RetrofitBuilders
                .createBaseChronicleRetrofitBuilder( "http://localhost:8090/rhizome/api/", httpClient ) )
                .build();
    }

    public static String quote( String s ) {
        return "\"" + s + "\"";
    }

    public static byte[] readBody( final RequestBody requestBody ) throws IOException {
        final Buffer buffer = new Buffer();
        if ( requestBody != null ) {
            requestBody.writeTo( buffer );
            return buffer.readByteArray();
        } else {
            return EMPTY_BYTES;
        }
    }
}
