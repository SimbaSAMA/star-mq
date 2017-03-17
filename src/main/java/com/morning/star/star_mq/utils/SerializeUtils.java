package com.morning.star.star_mq.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class SerializeUtils {
	
	public static <T> byte[] SerialObjInBean(T t) {
		if (null == t) {
			return null;
		} else {
			SerialObj<T> serial = new SerialObj<T>(t);
			@SuppressWarnings("unchecked")
			Schema<SerialObj<T>> schema = (Schema<SerialObj<T>>) RuntimeSchema.getSchema(serial.getClass());
			LinkedBuffer buffer = LinkedBuffer.allocate(1024);
			return ProtobufIOUtil.toByteArray(serial, schema, buffer);
		}
	}

	public static <T> byte[] SerialAndGzipObjInBean(T t) throws IOException {
		if (null == t) {
			return null;
		} else {
			SerialObj<T> serial = new SerialObj<T>(t);
			Schema schema = RuntimeSchema.getSchema(serial.getClass());
			LinkedBuffer buffer = LinkedBuffer.allocate(1024);
			return compress(ProtobufIOUtil.toByteArray(serial, schema, buffer));
		}
	}

	public static <T> T unSerialObjInBean(byte[] bytes, Class<T> t)
			throws InstantiationException, IllegalAccessException {
		if (null != bytes && bytes.length > 0) {
			SerialObj<T> serial = new SerialObj<T>(t.newInstance());
			Schema<SerialObj<T>> schema = (Schema<SerialObj<T>>) RuntimeSchema.getSchema(serial.getClass());
			ProtobufIOUtil.mergeFrom(bytes, serial, schema);
			return null != serial.getT() ? serial.getT() : null;
		} else {
			return null;
		}
	}

	public static <T> T unGZIPAndSerialObjInBean(byte[] bytes, Class<T> t)
			throws InstantiationException, IllegalAccessException, IOException {
		if (null != bytes && bytes.length > 0) {
			SerialObj<T> serial = new SerialObj<T>(t.newInstance());
			Schema schema = RuntimeSchema.getSchema(serial.getClass());
			ProtobufIOUtil.mergeFrom(new GZIPInputStream(new ByteArrayInputStream(bytes)), serial, schema);
			return null != serial.getT() ? serial.getT() : null;
		} else {
			return null;
		}
	}

	public static <T> T unGZIPAndSerialObjInBean(InputStream inputStream, Class<T> t)
			throws InstantiationException, IllegalAccessException, IOException {
		if (null != inputStream && inputStream.available() != 0) {
			SerialObj<T> serial = new SerialObj<T>(t.newInstance());
			Schema schema = RuntimeSchema.getSchema(serial.getClass());
			ProtobufIOUtil.mergeFrom(new GZIPInputStream(inputStream), serial, schema);
			return null != serial.getT() ? serial.getT() : null;
		} else {
			return null;
		}
	}

	public static <T> byte[] SerializeObject(T t) {
		if (null == t) {
			return null;
		} else {
			Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(t.getClass());
			LinkedBuffer buffer = LinkedBuffer.allocate(1024);
			return ProtobufIOUtil.toByteArray(t, schema, buffer);
		}
	}

	public static <T> T unSerializeObject(InputStream bytes, Class<T> t)
			throws InstantiationException, IllegalAccessException, IOException {
		if (null != bytes && bytes.available() != 0) {
			Schema<T> schema = RuntimeSchema.getSchema(t);
			T c = t.newInstance();
			ProtobufIOUtil.mergeFrom(bytes, c, schema);
			return c;
		} else {
			return null;
		}
	}

	public static <T> T unSerializeObject(byte[] bytes, Class<T> t)
			throws InstantiationException, IllegalAccessException {
		if (null != bytes && bytes.length > 0) {
			Schema<T> schema = RuntimeSchema.getSchema(t);
			T c = t.newInstance();
			ProtobufIOUtil.mergeFrom(bytes, c, schema);
			return c;
		} else {
			return null;
		}
	}

	public static <T> byte[] SerializeAndGZipObject(T t) throws IOException {
		if (null == t) {
			return null;
		} else {
			byte[] bytes = SerializeObject(t);
			return compress(bytes);
		}
	}

	public static <T> T UnGZipAndunSerializeObject(byte[] bytes, Class<T> t)
			throws IOException, InstantiationException, IllegalAccessException {
		return null != bytes && bytes.length > 0
				? unSerializeObject((InputStream) (new GZIPInputStream(new ByteArrayInputStream(bytes))), t) : null;
	}

	public static byte[] compress(byte[] bytes) throws IOException {
		if (null != bytes && bytes.length > 0) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GZIPOutputStream gos = new GZIPOutputStream(bos);
			gos.write(bytes);
			gos.flush();
			gos.close();
			bos.close();
			return bos.toByteArray();
		} else {
			return null;
		}
	}

	public static byte[] uncompress(byte[] bytes) throws IOException {
		if (null != bytes && bytes.length > 0) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			GZIPInputStream gis = new GZIPInputStream(in);
			byte[] buffer = new byte[1024];

			int count;
			while ((count = gis.read(buffer, 0, 1024)) != -1) {
				out.write(buffer, 0, count);
			}

			gis.close();
			in.close();
			out.close();
			return out.toByteArray();
		} else {
			return null;
		}
	}

	public static byte[] jdkSerializeAndGzip(Object object) throws IOException {
		return compress(jdkSerialize(object));
	}

	public static Object jdkUnGzipAndDeserialize(byte[] source) throws IOException, ClassNotFoundException {
		return jdkDeserialize(uncompress(source));
	}

	public static byte[] jdkSerialize(Object object) throws IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(256);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
		objectOutputStream.writeObject(object);

		try {
			objectOutputStream.flush();
			return byteStream.toByteArray();
		} catch (IOException arg3) {
			throw arg3;
		}
	}

	public static Object jdkDeserialize(byte[] source) throws ClassNotFoundException, IOException {
		ByteArrayInputStream byteStream = new ByteArrayInputStream(source);

		try {
			ObjectInputStream e = new ObjectInputStream(byteStream);
			return e.readObject();
		} catch (IOException | ClassNotFoundException arg2) {
			throw arg2;
		}
	}

	public static void main(String[] args) throws IOException {
		String temp = new String("Aaa");
		SerialObj<String> obj = new SerialObj<String>(temp);
		SerializeUtils.SerializeObject(obj);
//		System.out.println(new String(uncompress(compress("AAAAAAAAAAAAA".getBytes()))));
	}
	
}
