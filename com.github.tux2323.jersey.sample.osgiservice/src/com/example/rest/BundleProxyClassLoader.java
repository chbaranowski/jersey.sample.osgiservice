package com.example.rest;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.osgi.framework.Bundle;

/**
 * A BundleProxyClassLoader wraps a bundle and uses the various Bundle methods
 * to produce a ClassLoader.
 */
public class BundleProxyClassLoader extends ClassLoader {

	private Bundle bundle;
	private ClassLoader parent;

	public BundleProxyClassLoader(Bundle bundle) {
		final String method = "BundleProxyClassLoader() : ";

		this.bundle = bundle;

	}

	public BundleProxyClassLoader(Bundle bundle, ClassLoader parent) {
		super(parent);

		final String method = "BundleProxyClassLoader() : ";

		this.parent = parent;
		this.bundle = bundle;

	}

	@Override
	public Enumeration findResources(String name) throws IOException {
		final String method = "findResources() : ";
	
		Enumeration<?> resources = bundle.getResources(name);

		return resources;
	}

	@Override
	public URL findResource(String name) {
		final String method = "findResource() : ";
	
		URL resource = bundle.getResource(name);

		return resource;
	}

	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		final String method = "findClass() : ";

		Class<?> loadedClass = bundle.loadClass(name);

		return loadedClass;
	}

	@Override
	public URL getResource(String name) {
		final String method = "getResource() : ";

		URL resource;

		if (parent == null) {
			resource = findResource(name);
		} else {
			resource = super.getResource(name);
		}

		return resource;
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		final String method = "loadClass() : ";
	
		Class<?> clazz;
		if (parent == null) {
			clazz = findClass(name);
		} else {
			clazz = super.loadClass(name, false);
		}
		
		if (resolve) {
			super.resolveClass(clazz);
		}

		return clazz;
	}
}
