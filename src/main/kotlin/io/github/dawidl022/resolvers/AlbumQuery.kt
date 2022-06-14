package io.github.dawidl022.resolvers

import io.github.dawidl022.models.Album
import io.github.dawidl022.models.Albums

class AlbumQuery {
    fun albums(): List<Album> =
        Albums.all()
}
