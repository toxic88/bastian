package de.bastian.clan.client.message;

import de.bastian.clan.client.view.forum.EditPostView;
import de.bastian.clan.client.view.forum.EditThemeView;
import de.bastian.clan.client.view.forum.PostView;
import de.bastian.clan.client.view.forum.ThemesView;
import de.bastian.clan.client.view.picture.PictureView;
import de.bastian.clan.client.view.user.UserView;
import de.bastian.clan.client.view.user.UsersView;
import de.bastian.clan.client.view.widgets.ConfirmPopupPanel;

public interface AppMessages extends UsersView.UsersViewConstants,
                                     ThemesView.ThemesViewConstants,
                                     EditThemeView.EditThemeViewConstants,
                                     EditPostView.EditPostViewConstants,
                                     UserView.UserViewConstants,
                                     PictureView.PictureViewConstants,
                                     ConfirmPopupPanel.ConfirmPopupPanelConstants,
                                     PostView.PostViewConstants
{}
