package org.example;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BasicFunctionality {
	private final static String BUCKET = "bucket-20230815-2005";

	//  [Deleting files from bucket]
	protected static void deleteFrom(String key) {
		boolean doesExist = ClientS3Class.makeS3Client().doesObjectExist(BUCKET, key);
		if (!doesExist) {
			System.out.printf("\n---> Файл с ключом \"%s\" отсутствует.\n", key);
		} else {
			DeleteObjectRequest delete = new DeleteObjectRequest(BUCKET, key);
			ClientS3Class.makeS3Client().deleteObject(delete);
			System.out.printf("\n---> Файл с ключом \"%s\" успешно удален\n", key);
		}
	}

	//  [List of files in the bucket]
	protected static void listObjects() {
		ListObjectsRequest list = new ListObjectsRequest().withBucketName(BUCKET);
		ObjectListing listing = ClientS3Class.makeS3Client().listObjects(list);
		int counter = 1;
		System.out.println("\n---> Список всех файлов в хранилище:");
		for (S3ObjectSummary o : listing.getObjectSummaries()) {
			System.out.println(counter + ") " + o.getKey());
			counter++;
		}
		System.out.println();
	}

	//  [Uploading files to the bucket in parts (5 megabytes)]
	protected static void uploadInParts(String fullPath, String key) {
		boolean doesExist = ClientS3Class.makeS3Client().doesObjectExist(BUCKET, key);
		if (doesExist) {
			System.out.printf("\n---> Файл с ключом \"%s\" уже существует.\n", key);
		} else {
			InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(BUCKET, key);
			InitiateMultipartUploadResult initResponse = ClientS3Class.makeS3Client().initiateMultipartUpload(initRequest);

			long contentLength = new File(fullPath).length();
			long partSize = 5 * 1024 * 1024;
			long lengthFile = 0;

			List<PartETag> partETags = new ArrayList<>();

			try {
				long filePosition = 0;
				for (int i = 1; filePosition < contentLength; i++) {
					lengthFile = lengthFile + partSize;
					partSize = Math.min(partSize, (contentLength - filePosition));
					System.out.println("Осталось: " + (contentLength - lengthFile) + " байт");
					UploadPartRequest uploadRequest = new UploadPartRequest().withBucketName(BUCKET).withKey(key).withUploadId(initResponse.getUploadId()).withPartNumber(i).withFileOffset(filePosition).withFile(new File(fullPath)).withPartSize(partSize);

					UploadPartResult uploadResult = ClientS3Class.makeS3Client().uploadPart(uploadRequest);
					partETags.add(uploadResult.getPartETag());
					filePosition += partSize;
				}
				CompleteMultipartUploadRequest complete = new CompleteMultipartUploadRequest(initRequest.getBucketName(), initRequest.getKey(), initResponse.getUploadId(), partETags);

				ClientS3Class.makeS3Client().completeMultipartUpload(complete);
				System.out.printf("\n---> Файл с ключом \"%s\" успешно загружен.\n", key);
			} catch (SdkClientException e) {
				throw new RuntimeException("Возникла ошибка в ходе выгрузки файла в хранилище: ", e);
			}
		}
	}

	//  [Downloading a file from the bucket]
	protected static void downloadingFile(String key) {
		if (!ClientS3Class.makeS3Client().doesObjectExist(BUCKET, key)) {
			System.out.println("Данный элемент отсутствует в хранилище.");
		}
		final File whereToDownloadAFile = new File("C:\\Users\\mrart\\Рабочий стол" + "\\" + key);

		try (InputStream is = ClientS3Class.makeS3Client().getObject(new GetObjectRequest(BUCKET, key)).getObjectContent(); FileOutputStream fos = new FileOutputStream(whereToDownloadAFile)) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			long bytesCounter = 0;
			while ((bytesRead = is.read(buffer)) != -1) {
				System.out.println("Загружено байт: " + bytesCounter);
				bytesCounter += bytesRead;
				fos.write(buffer, 0, bytesRead);
			}
			System.out.printf("\n---> Файл успешно загружен в %s.\n", whereToDownloadAFile);
		} catch (Exception e) {
			throw new RuntimeException("\n--->Ошибка, файл не был загружен.\n", e);
		}
	}

	protected static String getFileExtension(String line) {
		String[] split = line.split(Pattern.quote("\\"));
		String lastWord = split[split.length - 1];
		String[] splitLastWord = lastWord.split("\\.");
		return "." + splitLastWord[splitLastWord.length - 1];
	}
}
