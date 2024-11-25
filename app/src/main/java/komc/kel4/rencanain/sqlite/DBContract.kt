package komc.kel4.rencanain.sqlite

import android.provider.BaseColumns

internal class DBContract {
    internal class RencanainTable: BaseColumns{
        companion object{
            const val TABLE_NAME = "rencanain"
            const val _ID = "_id"
            const val NAMA_PROYEK = "nama_projek"
            const val TANGGAL_MULAI = "tanggal_mulai"
            const val TANGGAL_SELESAI = "tanggal_selesai"
        }
    }
}