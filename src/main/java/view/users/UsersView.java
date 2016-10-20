package view.users;

import app.entities.UserEntity;
import utils.ResolveUrl;
import utils.libs.html.HtmlBlock;
import utils.writer.Writable;
import view.layouts.MasterLayout;

import java.util.Iterator;
import java.util.List;

public class UsersView extends MasterLayout {

	public UsersView(List<UserEntity> users) {
		super("Users",
                div(
                    container(
                        row(
                                column("col-md-8 col-md-offset-2",
                                       h3(text("Users")),
                                       list(users)
                                )
                        )

                    )
                ).withAttr("class", "jumbo"),
                container(
                        row(
                                column("col-md-8 col-md-offset-2", panel())
                        )
                )
			);
	}

    private static Writable list(List<UserEntity> users) {
        if(users == null || users.isEmpty()) return p(text("No users yet.."));

        Writable[] links = new Writable[users.size()];

        Iterator<UserEntity> itr = users.iterator();

        int n = 0;
        while (itr.hasNext()) {
            UserEntity user = itr.next();
            links[n++] =a(ResolveUrl.of("get.userByUsername", user.getUserName()), user.getUserName());
        }

        return listGroup(links);
    }

    private static Writable panel() {
        return panel("default", iconText("plus", "Create a new user"), form());
    }

    private static Writable form() {
        Writable content = new HtmlBlock(
                formGroup(
                        label("username", "Username", "col-sm-3"),
                          column("col-sm-9",
                                 input("text", "username", "username", "input-sm", true))),
                formGroup(
                        label("password", "Password", "col-sm-3"),
                        column("col-sm-9",
                               input("password", "password", "password", "input-sm", true))),
                formGroup(
                        label("email", "Email", "col-sm-3"),
                        column("col-sm-9",
                               input("email", "email", "email", "input-sm", true))),
                formGroup(
                        label("fullname", "Name", "col-sm-3"),
                        column("col-sm-9",
                               input("text", "fullname", "fullname", "input-sm", true))),
                formGroup(
                        column("col-sm-offset-2 col-sm-10 text-center", submit("Create", "primary"))
                )
        );

        return form(ResolveUrl.ofRoute("post.user"), "POST", "form-horizontal", content);
    }

}
