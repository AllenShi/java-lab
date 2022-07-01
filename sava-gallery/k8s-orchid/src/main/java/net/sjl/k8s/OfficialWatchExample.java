package net.sjl.k8s;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.reflect.TypeToken;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreApi;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1APIVersions;
import io.kubernetes.client.models.V1Namespace;
import io.kubernetes.client.models.V1NamespaceList;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Watch;

public class OfficialWatchExample {
	public static void main(String[] args) throws IOException, ApiException {
		ApiClient client = Config.defaultClient();
		client.getHttpClient().setReadTimeout(60, TimeUnit.SECONDS);

		InetSocketAddress proxyAddress = new InetSocketAddress("127.0.0.1", 8888);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddress);
		client.getHttpClient().setProxy(proxy);

		client.getJSON().setLenientOnJson(true);

		Configuration.setDefaultApiClient(client);

		CoreApi api = new CoreApi();
		try {
			V1APIVersions result = api.getAPIVersions();
			System.out.println(result);
		} catch (ApiException e) {
			System.err.println("Exception when calling CoreApi#getAPIVersions");
			e.printStackTrace();
		}

		CoreV1Api apiInstance = new CoreV1Api();

		try {
			// 1.0-beta2
			V1NamespaceList result = apiInstance.listNamespace(null, null, null, true, null, 56, null, 30, true);
			List<V1Namespace> namespaces = result.getItems();
			System.out.println("namespace count: " + namespaces.size());
			for (V1Namespace namespace : namespaces) {
				System.out.println(namespace.getKind() + "-->" + namespace.getStatus().getPhase());
			}
		} catch (ApiException e) {
			System.err.println("Exception when calling CoreV1Api#listNamespace");
			e.printStackTrace();
		}

		Watch<V1Namespace> watch = Watch.createWatch(client, apiInstance.listNamespaceCall(null, null, null,
				Boolean.TRUE, null, 200, null, 5, Boolean.TRUE, null, null),
				new TypeToken<Watch.Response<V1Namespace>>() {
				}.getType());

		for (Watch.Response<V1Namespace> item : watch) {
			System.out.printf("%s : %s%n", item.type, item.object.getMetadata().getName());
		}

	}
}
