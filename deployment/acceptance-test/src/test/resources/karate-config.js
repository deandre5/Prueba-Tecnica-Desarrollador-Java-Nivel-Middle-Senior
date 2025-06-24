function fn() {
  var env = karate.env || "dev";
  var connectTimeout = karate.properties["connectTimeout"] || 45000;

  var baseUrl =
    karate.properties["baseUrl"] ||
    "http://localhost:8092/api/v1";

  var config = {
    api: {
        baseUrl,
            path: {
            stats : "/stats"
            }
    }
    };

  karate.log("karate.env system property was: ", env);

  karate.configure('connectTimeout', 2000);
  karate.configure('readTimeout', 2000);
  karate.configure('ssl', true);
  return config;
}