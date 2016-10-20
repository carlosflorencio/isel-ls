package view.home;

import utils.ResolveUrl;
import utils.writer.Writable;
import view.layouts.MasterLayout;

public class HomeView extends MasterLayout {
    public HomeView() {
        super("Home",
              jumbo(),
              description()
        );
    }

    private static Writable jumbo() {
        return jumbotron(
                container(
                        img(ResolveUrl.asset("images/ImoProject.png")).withAttr("class", "img-responsive center logo")
                )
        );
    }

    private static Writable description() {
        Writable left = column("col-md-6 text-center", h2(icon("user")), p(text("Nice random text about users.")));
        Writable right = column("col-md-6 text-center", h2(icon("map-marker")), p(text("Another nice random text about properties..")));

        return container(row(left, right));
    }

}
