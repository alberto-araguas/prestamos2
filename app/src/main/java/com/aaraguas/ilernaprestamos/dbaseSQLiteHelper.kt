package com.aaraguas.ilernaprestamos

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbaseSQLiteHelper (context: Context) : SQLiteOpenHelper(
    context, "prestamos.db", null, 12) {

    private lateinit var db : SQLiteDatabase

    companion object {
        val TABLA_PRESTAMOS = "equipo"
        val CAMPO_ID = "_id"
        val CAMPO_NOMBREEQUIPO = "nombre"
        val CAMPO_CARACTERISTICAS = "caracteristicas"
        val CAMPO_FECHACOMPRA = "fecha_compra"
        val CAMPO_FKPROVEEDOREQUIPO = "id_proveedor"
        val CAMPO_FKFAMILIAEQUIPO = "id_familia"
        val CAMPO_ESTADOPRESTAMO = "estado_prestado"
        val CAMPO_FECHAPRESTAMO = "fecha_prestamo"
        val CAMPO_FKLUGARPRESTAMO = "id_lugar_prestamo"
        val CAMPO_FKUSUARIOPRESTAMO = "id_usuario"
        //tabla familia
        val TABLA_FAMILIA = "familia"
        val CAMPO_IDF = "_idF"
        val CAMPO_FNOMBRE = "familia_nombre"
        //tabla lugares
        val TABLA_LUGARES = "lugares"
        val CAMPO_IDL = "_idL"
        val CAMPO_LNOMBRE = "lugares_nombre"
        val CAMPO_LDIRECCION = "lugares_direccion"
        val CAMPO_LTELEFONO = "lugares_telefono"
        val CAMPO_LCORREO = "lugares_correo"
        //tabla proveedores
        val TABLA_PROVEEDORES = "proveedores"
        val CAMPO_IDP = "_idP"
        val CAMPO_PNOMBRE = "proveedores_nombre"
        val CAMPO_PDIRECCION = "proveedores_direccion"
        val CAMPO_PTELEFONO = "proveedores_telefono"
        val CAMPO_PCORREO = "proveedores_correo"
        val CAMPO_PCONTACTO = "proveedores_contacto"
        //tabla usuarios
        val TABLA_USUARIOS = "usuarios"
        val CAMPO_IDUS = "_idUsuario"
        val CAMPO_USNOMBRE = "usuarios_nombre"
        val CAMPO_USDIRECCION = "usuarios_direccion"
        val CAMPO_USTELEFONO = "usuarios_telefono"
        val CAMPO_USCORREO = "usuarios_correo"
        val CAMPO_USDEPARTAMENTO = "usuarios_departamento"



    }

    override fun onCreate(db: SQLiteDatabase?) {

        val ordenCreacionFamilia = "CREATE TABLE $TABLA_FAMILIA " +
                "($CAMPO_IDF INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_FNOMBRE TEXT )"

        db?.execSQL(ordenCreacionFamilia)

        val ordenCreacionlugares = "CREATE TABLE $TABLA_LUGARES " +
                "($CAMPO_IDL INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_LNOMBRE TEXT, " +
                "$CAMPO_LDIRECCION TEXT, $CAMPO_LTELEFONO LONG , $CAMPO_LCORREO TEXT)"
        db?.execSQL(ordenCreacionlugares)

        val ordenCreacionproveedores = "CREATE TABLE $TABLA_PROVEEDORES " +
                "($CAMPO_IDP INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_PNOMBRE TEXT, " +
                "$CAMPO_PDIRECCION TEXT, $CAMPO_PTELEFONO LONG , $CAMPO_PCORREO TEXT, $CAMPO_PCONTACTO TEXT)"
        db?.execSQL(ordenCreacionproveedores)

        val ordenCreacionusuarios = "CREATE TABLE $TABLA_USUARIOS " +
                "($CAMPO_IDUS INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_USNOMBRE TEXT, " +
                "$CAMPO_USDIRECCION TEXT, $CAMPO_USTELEFONO LONG , $CAMPO_USCORREO TEXT, $CAMPO_USDEPARTAMENTO TEXT)"
        db?.execSQL(ordenCreacionusuarios)

        val ordenCreacionequipos = "CREATE TABLE $TABLA_PRESTAMOS " +
                "($CAMPO_ID INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOMBREEQUIPO TEXT, " +
                "$CAMPO_CARACTERISTICAS TEXT, $CAMPO_FECHACOMPRA TEXT , $CAMPO_FKPROVEEDOREQUIPO INTEGER, " +
                "$CAMPO_FKFAMILIAEQUIPO INTEGER, $CAMPO_ESTADOPRESTAMO BOOLEAN, $CAMPO_FECHAPRESTAMO TEXT, " +
                "$CAMPO_FKLUGARPRESTAMO INTEGER, $CAMPO_FKUSUARIOPRESTAMO INTEGER )"
        db?.execSQL(ordenCreacionequipos)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val ordenBorrado = "DROP TABLE IF EXISTS discos"
        db?.execSQL(ordenBorrado)
        val ordenBorradoFamilia = "DROP TABLE IF EXISTS familia"
        db?.execSQL(ordenBorradoFamilia)
        val ordenBorradoLugares = "DROP TABLE IF EXISTS lugares"
        db?.execSQL(ordenBorradoLugares)
        val ordenBorradoProveedores = "DROP TABLE IF EXISTS proveedores"
        db?.execSQL(ordenBorradoProveedores)
        val ordenBorradoUsuario = "DROP TABLE IF EXISTS usuarios"
        db?.execSQL(ordenBorradoUsuario)
        val ordenBorradoEquipo = "DROP TABLE IF EXISTS equipos"
        db?.execSQL(ordenBorradoEquipo)
        val ordenBorradoEquipo2 = "DROP TABLE IF EXISTS equipo"
        db?.execSQL(ordenBorradoEquipo2)
        onCreate(db)
    }

    fun obtenerTodosPrestamos(tabla: String, campoOrden: String): Cursor {
        db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tabla ORDER BY $campoOrden", null)
        return cursor
    }
    fun obtenerSoloPrestados(): Cursor {
        db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLA_PRESTAMOS WHERE $CAMPO_ESTADOPRESTAMO = 1 ORDER BY $CAMPO_ID", null)
        return cursor
    }
    fun obtenerNoPrestados(): Cursor {
        db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLA_PRESTAMOS WHERE $CAMPO_ESTADOPRESTAMO = 0 ORDER BY $CAMPO_ID", null)
        return cursor
    }

    fun obtenerPorId(tabla: String, id: Int) : Cursor {
        db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tabla WHERE $CAMPO_ID = $id", null)
        cursor.moveToFirst()
        return cursor
    }

    fun obtenerCursor(tabla: String, campo: String, campoValor: String,
                      campoOrden: String): Cursor {
        db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tabla " +
                "WHERE $campo LIKE '%$campoValor%' " +
                "ORDER BY $campoOrden", null)
        return cursor
    }

    fun agregarPrestamo(equipo: String, caracteristk: String, fechacompra: String, proveedor: Int,
                        familia: Int, Estado: Boolean, fechaprest: String, lugar: Int, usuario: Int) {
        val datos = ContentValues()
        datos.put(CAMPO_NOMBREEQUIPO, equipo)
        datos.put(CAMPO_CARACTERISTICAS, caracteristk)
        datos.put(CAMPO_FECHACOMPRA, fechacompra)
        datos.put(CAMPO_FKPROVEEDOREQUIPO,proveedor)
        datos.put(CAMPO_FKFAMILIAEQUIPO, familia)
        datos.put(CAMPO_ESTADOPRESTAMO, Estado)
        datos.put(CAMPO_FECHAPRESTAMO, fechaprest)
        datos.put(CAMPO_FKLUGARPRESTAMO, lugar)
        datos.put(CAMPO_FKUSUARIOPRESTAMO, usuario)

        val db = this.writableDatabase
        db.insert(TABLA_PRESTAMOS, null, datos)
        db.close()
    }


    fun editarPrestamo(id: Int, equipo: String, caracteristk: String, fechacompra: String, proveedor: Int,
                       familia: Int, Estado: Boolean, fechaprest: String, lugar: Int, usuario: Int) {
        val args = arrayOf(id.toString())
        val datos = ContentValues()
        datos.put(CAMPO_NOMBREEQUIPO, equipo)
        datos.put(CAMPO_CARACTERISTICAS, caracteristk)
        datos.put(CAMPO_FECHACOMPRA, fechacompra)
        datos.put(CAMPO_FKPROVEEDOREQUIPO,proveedor)
        datos.put(CAMPO_FKFAMILIAEQUIPO, familia)
        datos.put(CAMPO_ESTADOPRESTAMO, Estado)
        datos.put(CAMPO_FECHAPRESTAMO, fechaprest)
        datos.put(CAMPO_FKLUGARPRESTAMO, lugar)
        datos.put(CAMPO_FKUSUARIOPRESTAMO, usuario)
        val db = this.writableDatabase
        db.update(TABLA_PRESTAMOS, datos, "$CAMPO_ID = ?", args)
        db.close()
    }

    fun borrarDisco(id: Int) : Int {
        val args = arrayOf(id.toString())
        val db = this.writableDatabase
        val borrados = db.delete(TABLA_PRESTAMOS, "$CAMPO_ID = ?", args)
        db.close()
        return borrados
    }



    //CRUD TABLA LUGARES
    fun agregarLugar(lugar_nombre: String,lugar_direccion: String,lugar_telefono: Long,lugar_correo: String) {
        val datos = ContentValues()
        datos.put(CAMPO_LNOMBRE, lugar_nombre)
        datos.put(CAMPO_LDIRECCION, lugar_direccion)
        datos.put(CAMPO_LTELEFONO, lugar_telefono)
        datos.put(CAMPO_LCORREO, lugar_correo)

        val db = this.writableDatabase
        db.insert(TABLA_LUGARES, null, datos)
        db.close()
    }
    @SuppressLint("Recycle")
    fun cargarNombresLugares():ArrayList<String> {
        val miLista = ArrayList<String>()
        db = this.readableDatabase
        val cursor =
            db.rawQuery("SELECT $CAMPO_LNOMBRE FROM $TABLA_LUGARES ORDER BY $CAMPO_IDL", null)
        cursor.moveToFirst()
        do{
            miLista.add(cursor.getString(0))
        }  while (cursor.moveToNext())
        db.close()
        return miLista
    }



    //CRUD TABLA FAMILIA
    fun agregarFamilia(lugar_nombre: String) {
        val datos = ContentValues()
        datos.put(CAMPO_FNOMBRE, lugar_nombre)

        val db = this.writableDatabase
        db.insert(TABLA_FAMILIA, null, datos)
        db.close()
    }
    @SuppressLint("Recycle")
    fun cargarNombresFamilias():ArrayList<String> {
        val miLista = ArrayList<String>()
        db = this.readableDatabase
        val cursor =
            db.rawQuery("SELECT $CAMPO_FNOMBRE FROM $TABLA_FAMILIA ORDER BY $CAMPO_IDF", null)
        cursor.moveToFirst()
        do{
            miLista.add(cursor.getString(0))
        }  while (cursor.moveToNext())
        db.close()
        return miLista
    }


    //CRUD TABLA PROVEEDORES
    fun agregarProveedor(prov_nombre: String,prov_direccion: String,prov_telefono: Long,prov_correo: String,prov_contacto: String) {
        val datos = ContentValues()
        datos.put(CAMPO_PNOMBRE, prov_nombre)
        datos.put(CAMPO_PDIRECCION, prov_direccion)
        datos.put(CAMPO_PTELEFONO, prov_telefono)
        datos.put(CAMPO_PCORREO, prov_correo)
        datos.put(CAMPO_PCONTACTO, prov_contacto)

        val db = this.writableDatabase
        db.insert(TABLA_PROVEEDORES, null, datos)
        db.close()
    }

    @SuppressLint("Recycle")
    fun cargarNombresProveedores():ArrayList<String>{
        val miLista = ArrayList<String>()

        db = this.readableDatabase
        val cursoru = db.rawQuery("SELECT $CAMPO_PNOMBRE FROM $TABLA_PROVEEDORES ORDER BY $CAMPO_IDP" , null)

       cursoru.moveToFirst()
        do{
            miLista.add(cursoru.getString(0))
        }  while (cursoru.moveToNext())
        db.close()
        return miLista
    }




    //CRUD TABLA USUARIOS
    fun agregarUsuario(us_nombre: String,us_direccion: String,us_telefono: Long,us_correo: String,us_departamento: String) {
        val datos = ContentValues()
        datos.put(CAMPO_USNOMBRE, us_nombre)
        datos.put(CAMPO_USDIRECCION, us_direccion)
        datos.put(CAMPO_USTELEFONO, us_telefono)
        datos.put(CAMPO_USCORREO, us_correo)
        datos.put(CAMPO_USDEPARTAMENTO, us_departamento)

        val db = this.writableDatabase
        db.insert(TABLA_USUARIOS, null, datos)
        db.close()
    }
    @SuppressLint("Recycle")
    fun cargarNombresUsuarios():ArrayList<String>{
        val miLista = ArrayList<String>()
        db = this.readableDatabase
        val cursoru = db.rawQuery("SELECT $CAMPO_USNOMBRE FROM $TABLA_USUARIOS ORDER BY $CAMPO_IDUS" , null)
        cursoru.moveToFirst()
        do{
            miLista.add(cursoru.getString(0))
        }  while (cursoru.moveToNext())
        db.close()
        return miLista
    }

    fun cerrarDB() {
        db.close()
    }


}