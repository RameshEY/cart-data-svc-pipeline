package com.bbs.cart.configuration;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.cluster.ClusterInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.couchbase.config.AbstractReactiveCouchbaseConfiguration;
import org.springframework.data.couchbase.config.BeanNames;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.core.mapping.CouchbaseDocument;
import org.springframework.data.couchbase.core.mapping.CouchbasePersistentEntity;
import org.springframework.data.couchbase.core.mapping.CouchbasePersistentProperty;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.data.mapping.context.MappingContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCouchbaseRepositories
public class CouchbaseConfiguration extends AbstractReactiveCouchbaseConfiguration {

    @Value("${spring.couchbase.bootstrap-hosts}")
    String[] hosts;

    @Value("${spring.couchbase.bucket.name}")
    String bucketName;

    @Value("${spring.couchbase.bucket.password}")
    String password;

    @Value("${spring.couchbase.bucket.username}")
    String username;

    @Override
    protected List<String> getBootstrapHosts() {
        return Arrays.asList(hosts);
    }

    @Override
    protected String getBucketName() {
        return bucketName;
    }

    @Override
    protected String getBucketPassword() {
        return password;
    }

    @Override
    protected String getUsername() {
        return username;
    }

    @Override
    @Bean(name = BeanNames.COUCHBASE_CLUSTER_INFO)
    public ClusterInfo couchbaseClusterInfo() throws Exception {
        return couchbaseCluster().authenticate(getUsername(), getBucketPassword())
                .clusterManager()
                .info();
    }

    @Override
    @Bean(destroyMethod = "close", name = BeanNames.COUCHBASE_BUCKET)
    public Bucket couchbaseClient() throws Exception {
        return couchbaseCluster().openBucket(getBucketName());
    }


    @WritingConverter
    public static enum BigDecimalToString implements Converter<BigDecimal, String> {
        INSTANCE;

        @Override
        public String convert(BigDecimal source) {
            // or a more appropriate implementation
            return source.toString();
        }


    }

    @ReadingConverter
    public static enum StringToBigDecimalConverter implements Converter<String, BigDecimal> {
        INSTANCE;

        @Override
        public BigDecimal convert(String source) {
            return new BigDecimal(source);
        }

    }

    @Override
    @Bean
    public MappingCouchbaseConverter mappingCouchbaseConverter() throws Exception {
        MappingCouchbaseConverter converter = new ExpiringDocumentCouchbaseConverter(couchbaseMappingContext());
        converter.setCustomConversions(customConversions());
        return converter;
    }

    class ExpiringDocumentCouchbaseConverter extends MappingCouchbaseConverter {
        public ExpiringDocumentCouchbaseConverter(MappingContext<? extends CouchbasePersistentEntity<?>, CouchbasePersistentProperty> mappingContext) {
            super(mappingContext);
        }

        @Override
        public void write(final Object source, final CouchbaseDocument target) {
            super.write(source, target);
            if (source instanceof Object) {
                target.setExpiration(1222);
            }
        }
    }
}
