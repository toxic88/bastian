package de.bastian.clan.client.message;

import de.bastian.clan.client.view.forum.EditPostView;
import de.bastian.clan.client.view.forum.EditThemeView;
import de.bastian.clan.client.view.forum.ThemesView;
import de.bastian.clan.client.view.user.EditUserView;
import de.bastian.clan.client.view.user.UsersView;

public interface AppMessages extends EditUserView.EditUserConstants,
                                     UsersView.UsersViewConstants,
                                     ThemesView.ThemesViewConstants,
                                     EditThemeView.EditThemeViewConstants,
                                     EditPostView.EditPostViewConstants
{}
