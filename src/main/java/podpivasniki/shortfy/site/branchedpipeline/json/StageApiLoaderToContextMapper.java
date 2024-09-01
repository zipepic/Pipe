package podpivasniki.shortfy.site.branchedpipeline.json;

import lombok.SneakyThrows;
import podpivasniki.shortfy.site.branchedpipeline.stage.IStageContext;
import podpivasniki.shortfy.site.branchedpipeline.stage.configchain.StageContextConfigHandler;

class StageApiLoaderToContextMapper implements StageContextConfigHandler {
    private final ApiLoader apiLoader;
    private final Services services;

    public StageApiLoaderToContextMapper(ApiLoader apiLoader, Services services) {
        this.apiLoader = apiLoader;
        this.services = services;
    }

    @SneakyThrows
    @Override
    public void handle(IStageContext context) {
        for(String clazz: services.getApiClasses()){
            context.registerBean(apiLoader.loadApiG(Class.forName(clazz)));
        }
    }
}
