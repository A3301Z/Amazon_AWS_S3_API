package org.example;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class ClientS3Class {
	public static void main(String[] args) throws InterruptedException {
		CommandHandler.commandHandler();
	}

	protected static AmazonS3 makeS3Client() {
		final ParseENV.Credentials credentials = ParseENV.parseEnvFile2();
		final String ACCESS_KEY = credentials.ACCESS_KEY();
		final String SECRET_KEY = credentials.SECRET_KEY();
		final AWSCredentialsProvider credentialsAWS = new AWSStaticCredentialsProvider(new BasicAWSCredentials(
				ACCESS_KEY, SECRET_KEY));
		final String nameSpace = "axxosfok1a37";
		final String region = "eu-amsterdam-1";
		final String endpoint = String.format("%s.compat.objectstorage.%s.oraclecloud.com", nameSpace, region);
		final AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(credentialsAWS)
				.withEndpointConfiguration(endpointConfiguration)
				.disableChunkedEncoding()
				.enablePathStyleAccess()
				.build();
	}
}
