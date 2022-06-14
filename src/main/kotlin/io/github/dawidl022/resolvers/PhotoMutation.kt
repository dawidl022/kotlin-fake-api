package io.github.dawidl022.resolvers

import io.github.dawidl022.models.Photo
import io.github.dawidl022.models.Photos

class PhotoMutation {
    fun createPhoto(photo: Photo) =
        Photos.create(photo)

    fun updatePhoto(id: Int, photo: Photo) =
        Photos.put(id, photo)

    fun deletePhoto(id: Int) =
        Photos.delete(id)
}
