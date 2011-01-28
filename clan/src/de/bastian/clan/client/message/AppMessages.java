package de.bastian.clan.client.message;

import de.bastian.clan.client.forum.view.EditPostView;
import de.bastian.clan.client.forum.view.EditThemeView;
import de.bastian.clan.client.forum.view.PostView;
import de.bastian.clan.client.forum.view.ThemesView;
import de.bastian.clan.client.picture.view.PictureView;
import de.bastian.clan.client.user.view.UserView;
import de.bastian.clan.client.user.view.UsersView;
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
