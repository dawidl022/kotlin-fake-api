package io.github.dawidl022.resolvers

import io.github.dawidl022.models.Albums
import io.github.dawidl022.models.Photos
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class PhotoQuery {
    fun photos() =
        Photos.all()

    fun photo(id: Int) =
        Photos.get(id)

    fun photosByAlbum(albumId: Int) =
        transaction {
            Photos.select {
                Photos.albumId eq albumId
            }.map(Photos::fromRow)
        }

    fun photosByUser(userId: Int) =
        transaction {
            (Photos innerJoin Albums)
                .slice(Photos.columns)
                .select { Albums.userId eq userId }
                .map(Photos::fromRow)
        }
}
