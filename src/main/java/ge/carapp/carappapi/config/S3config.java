package ge.carapp.carappapi.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


@Data
@Configuration
@ConfigurationProperties(prefix = "aws-s3")
public class S3config {
    private String region;
    private String mediaFilesBucket;
    private String cloudfrontDistribution;

    @Bean
    protected S3Client createS3Client() {
        Region awsRegion = Region.of(this.region);
        return S3Client.builder()
                .region(awsRegion)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean("mediaFilesBucket")
    protected String mediaFilesBucket(){
        return mediaFilesBucket;
    }

    @Bean("cloudfrontDistribution")
    protected String cloudfrontDistribution(){
        return cloudfrontDistribution;
    }
}
