package io.github.dawidl022.resolvers

import io.github.dawidl022.models.Album
import io.github.dawidl022.models.Albums

class AlbumMutation {
    fun createAlbum(album: Album): Album =
        Albums.create(album)

    fun updateAlbum(id: Int, album: Album): Album =
        Albums.put(id, album)

    fun deleteAlbum(id: Int): Album =
        Albums.delete(id)
}
