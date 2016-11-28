package com.gestorventas.database;

import com.gestorventas.tablas.TCliente;
import com.gestorventas.tablas.TConfiguracion;
import com.gestorventas.tablas.TPedidoCab;
import com.gestorventas.tablas.TPedidoDet;
import com.gestorventas.tablas.TProducto;
import com.gestorventas.tablas.TUsuario;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class DatabaseProvider extends ContentProvider{
	
	private DatabaseHandler mDB;

    private static final String AUTHORITY = "com.gestorventas.database.DatabaseProvider";
    
    //Usuario
    public static final Uri USUARIO_CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + TUsuario.NOMBRE_TABLA);
    public static final String USUARIO_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/mt-" + TUsuario.NOMBRE_TABLA;
    public static final String USUARIO_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/mt-" + TUsuario.NOMBRE_TABLA;
    
    //Configuracion
    public static final Uri CONFIGURACION_CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + TConfiguracion.NOMBRE_TABLA);
    public static final String CONFIGURACION_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/mt-" + TConfiguracion.NOMBRE_TABLA;
    public static final String CONFIGURACION_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/mt-" + TConfiguracion.NOMBRE_TABLA;
	//Cliente
	public static final Uri CLIENTE_CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TCliente.NOMBRE_TABLA);
	public static final String CLIENTE_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/mt-" + TCliente.NOMBRE_TABLA;
	public static final String CLIENTE_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/mt-" + TCliente.NOMBRE_TABLA;

	//Producto
	public static final Uri PRODUCTO_CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TProducto.NOMBRE_TABLA);
	public static final String PRODUCTO_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/mt-" + TProducto.NOMBRE_TABLA;
	public static final String PRODUCTO_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/mt-" + TProducto.NOMBRE_TABLA;

	//Pedido
	public static final Uri PEDIDO_CABECERA_CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TPedidoCab.NOMBRE_TABLA);
	public static final String PEDIDO_CABECERA_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/mt-" + TPedidoCab.NOMBRE_TABLA;
	public static final String PEDIDO_CABECERA_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/mt-" + TPedidoCab.NOMBRE_TABLA;


	//Pedido DETALLE
	public static final Uri PEDIDO_DETALLE_CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TPedidoDet.NOMBRE_TABLA);
	public static final String PEDIDO_DETALLE__CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/mt-" + TPedidoDet.NOMBRE_TABLA;
	public static final String PEDIDO_DETALLE_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/mt-" + TPedidoDet.NOMBRE_TABLA;
    
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    
    static {
    	sURIMatcher.addURI(AUTHORITY, TUsuario.NOMBRE_TABLA, TUsuario.SELECT_ALL);
    	sURIMatcher.addURI(AUTHORITY, TUsuario.NOMBRE_TABLA + "/#", TUsuario.SELECT_DISTINCT);
    	
    	sURIMatcher.addURI(AUTHORITY, TConfiguracion.NOMBRE_TABLA, TConfiguracion.SELECT_ALL);
    	sURIMatcher.addURI(AUTHORITY, TConfiguracion.NOMBRE_TABLA + "/#", TConfiguracion.SELECT_DISTINCT);

		sURIMatcher.addURI(AUTHORITY, TCliente.NOMBRE_TABLA, TCliente.SELECT_ALL);
		sURIMatcher.addURI(AUTHORITY, TCliente.NOMBRE_TABLA + "/#", TCliente.SELECT_DISTINCT);

		sURIMatcher.addURI(AUTHORITY, TProducto.NOMBRE_TABLA, TProducto.SELECT_ALL);
		sURIMatcher.addURI(AUTHORITY, TProducto.NOMBRE_TABLA + "/#", TProducto.SELECT_DISTINCT);

		sURIMatcher.addURI(AUTHORITY, TPedidoCab.NOMBRE_TABLA, TPedidoCab.SELECT_ALL);
		sURIMatcher.addURI(AUTHORITY, TPedidoCab.NOMBRE_TABLA + "/#", TPedidoCab.SELECT_DISTINCT);

		sURIMatcher.addURI(AUTHORITY, TPedidoDet.NOMBRE_TABLA, TPedidoDet.SELECT_ALL);
		sURIMatcher.addURI(AUTHORITY, TPedidoDet.NOMBRE_TABLA + "/#", TPedidoDet.SELECT_DISTINCT);

    }
	
	
	@Override
	public boolean onCreate() {
		 mDB = new DatabaseHandler(getContext());
	     return true;
	}
	
	@Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        sqlDB.execSQL("PRAGMA foreign_keys=OFF;");
        int rowsAffected = 0;
        String id;
        switch (uriType) {
	      //Usuario
	        case TUsuario.SELECT_ALL:
	            rowsAffected = sqlDB.delete(TUsuario.NOMBRE_TABLA,
	                    selection, selectionArgs);
	            break;
	        case TUsuario.SELECT_DISTINCT:
	            id = uri.getLastPathSegment();
	            if (TextUtils.isEmpty(selection)) {
	                rowsAffected = sqlDB.delete(TUsuario.NOMBRE_TABLA,
	                		TUsuario.COL_ROWID + "=" + id, null);
	            } else {
	                rowsAffected = sqlDB.delete(TUsuario.NOMBRE_TABLA,
	                        selection + " and " + TUsuario.COL_ROWID + "=" + id,
	                        selectionArgs);
	            }
	            break;
			//Configuracion
	        case TConfiguracion.SELECT_DISTINCT:
	            id = uri.getLastPathSegment();
	            if (TextUtils.isEmpty(selection)) {
	                rowsAffected = sqlDB.delete(TConfiguracion.NOMBRE_TABLA,
	                		TConfiguracion.COL_ROWID + "=" + id, null);
	            } else {
	                rowsAffected = sqlDB.delete(TConfiguracion.NOMBRE_TABLA,
	                        selection + " and " + TConfiguracion.COL_ROWID + "=" + id,
	                        selectionArgs);
	            }
	            break;
			//Cliente
			case TCliente.SELECT_DISTINCT:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsAffected = sqlDB.delete(TCliente.NOMBRE_TABLA,
							TCliente.COL_ROWID + "=" + id, null);
				} else {
					rowsAffected = sqlDB.delete(TCliente.NOMBRE_TABLA,
							selection + " and " + TCliente.COL_ROWID + "=" + id,
							selectionArgs);
				}
				break;
			//PRODUCTO
			case TProducto.SELECT_DISTINCT:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsAffected = sqlDB.delete(TProducto.NOMBRE_TABLA,
							TProducto.COL_ROWID + "=" + id, null);
				} else {
					rowsAffected = sqlDB.delete(TProducto.NOMBRE_TABLA,
							selection + " and " + TProducto.COL_ROWID + "=" + id,
							selectionArgs);
				}
				break;
			//PEDIDO CAB
			case TPedidoCab.SELECT_DISTINCT:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsAffected = sqlDB.delete(TPedidoCab.NOMBRE_TABLA,
							TPedidoCab.COL_ROWID + "=" + id, null);
				} else {
					rowsAffected = sqlDB.delete(TPedidoCab.NOMBRE_TABLA,
							selection + " and " + TPedidoCab.COL_ROWID + "=" + id,
							selectionArgs);
				}
				break;
			//PEDIDO CAB
			case TPedidoDet.SELECT_DISTINCT:
				id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsAffected = sqlDB.delete(TPedidoDet.NOMBRE_TABLA,
							TPedidoDet.COL_ROWID + "=" + id, null);
				} else {
					rowsAffected = sqlDB.delete(TPedidoDet.NOMBRE_TABLA,
							selection + " and " + TPedidoDet.COL_ROWID + "=" + id,
							selectionArgs);
				}
				break;
	        default:
	            throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
	}

	@Override
	public String getType(Uri uri) {
		int uriType = sURIMatcher.match(uri);
        switch (uriType) {
	      //Usuario
	        case TUsuario.SELECT_ALL:
	            return TUsuario.NOMBRE_TABLA;
	        case TUsuario.SELECT_DISTINCT:
	            return TUsuario.NOMBRE_TABLA;
	          //Configuracion
	        case TConfiguracion.SELECT_ALL:
	            return CONFIGURACION_CONTENT_TYPE;
	        case TConfiguracion.SELECT_DISTINCT:
	            return CONFIGURACION_CONTENT_ITEM_TYPE;
			//Cliente
			case TCliente.SELECT_ALL:
				return CLIENTE_CONTENT_TYPE;
			case TCliente.SELECT_DISTINCT:
				return CLIENTE_CONTENT_ITEM_TYPE;
			//Producto
			case TProducto.SELECT_ALL:
				return PRODUCTO_CONTENT_TYPE;
			case TProducto.SELECT_DISTINCT:
				return PRODUCTO_CONTENT_ITEM_TYPE;
			//PEDIDO CAB
			case TPedidoCab.SELECT_ALL:
				return PEDIDO_CABECERA_CONTENT_TYPE;
			case TPedidoCab.SELECT_DISTINCT:
				return PEDIDO_CABECERA_CONTENT_ITEM_TYPE;
			//PEDIDO CAB
			case TPedidoDet.SELECT_ALL:
				return PEDIDO_DETALLE_CONTENT_TYPE;
			case TPedidoDet.SELECT_DISTINCT:
				return PEDIDO_DETALLE__CONTENT_ITEM_TYPE;
	        default:
	            return null;
        }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		if (uriType != TUsuario.SELECT_ALL && uriType != TConfiguracion.SELECT_ALL &&
            uriType != TCliente.SELECT_ALL && uriType != TProducto.SELECT_ALL &&
			uriType != TPedidoCab.SELECT_ALL && uriType != TPedidoCab.SELECT_ALL &&
				uriType != TPedidoDet.SELECT_ALL && uriType != TPedidoDet.SELECT_ALL) {
        throw new IllegalArgumentException("URI inválida para la inserción.");
	    }
	    SQLiteDatabase sqlDB = mDB.getWritableDatabase();
	    sqlDB.execSQL("PRAGMA foreign_keys=OFF;");
	    long newID = sqlDB.insertOrThrow(getType(uri,true), null,values);
	    if (newID > 0) {
	        Uri newUri = ContentUris.withAppendedId(uri, newID);
	        getContext().getContentResolver().notifyChange(uri, null);
	        return newUri;
	    } else {
	        throw new SQLException("No se pudo insertar la fila en " + uri);
	    } 
			
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String inTables;
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
        	case TUsuario.SELECT_DISTINCT:
        		queryBuilder.setTables(TUsuario.NOMBRE_TABLA);
            	// filtro
                queryBuilder.appendWhere(TUsuario.COL_ROWID + "="
                        + uri.getLastPathSegment());
                break;
        	case TUsuario.SELECT_ALL:
            	// tabla
            	queryBuilder.setTables(TUsuario.NOMBRE_TABLA);
                // sin filtro
                break;
                //Configuracion
            case TConfiguracion.SELECT_DISTINCT:
            	// tabla
            	queryBuilder.setTables(TConfiguracion.NOMBRE_TABLA);
            	// filtro
                queryBuilder.appendWhere(TConfiguracion.COL_ROWID + "="
                        + uri.getLastPathSegment());
                break;
            case TConfiguracion.SELECT_ALL:
            	// tabla
            	queryBuilder.setTables(TConfiguracion.NOMBRE_TABLA);
                // sin filtro
                break;
			//Cliente
			case TCliente.SELECT_DISTINCT:
				// tabla
				queryBuilder.setTables(TCliente.NOMBRE_TABLA);
				// filtro
				queryBuilder.appendWhere(TCliente.COL_ROWID + "="
						+ uri.getLastPathSegment());
				break;
			case TCliente.SELECT_ALL:
				// tabla
				queryBuilder.setTables(TCliente.NOMBRE_TABLA);
				// sin filtro
				break;
			//Producto
			case TProducto.SELECT_DISTINCT:
				// tabla
				queryBuilder.setTables(TProducto.NOMBRE_TABLA);
				// filtro
				queryBuilder.appendWhere(TProducto.COL_ROWID + "="
						+ uri.getLastPathSegment());
				break;
			case TProducto.SELECT_ALL:
				// tabla
				queryBuilder.setTables(TProducto.NOMBRE_TABLA);
				// sin filtro
				break;
			case TPedidoCab.SELECT_ALL:
				// tabla
				queryBuilder.setTables(TPedidoCab.NOMBRE_TABLA);
				// sin filtro
				break;
			case TPedidoDet.SELECT_ALL:
				// tabla
				queryBuilder.setTables(TPedidoDet.NOMBRE_TABLA);
				// sin filtro
				break;
        	 default:
                 throw new IllegalArgumentException("URI invalido para el Select");
        }
        
        Cursor cursor;
        
        cursor = queryBuilder.query(mDB.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        
        return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        sqlDB.execSQL("PRAGMA foreign_keys=OFF;");

        int rowsAffected;
        String id;
        StringBuilder modSelection;

        switch (uriType) {
        	//Usuario
	        case TUsuario.SELECT_DISTINCT:
	            id = uri.getLastPathSegment();
	            modSelection =
	            		new StringBuilder(TUsuario.COL_ROWID + "=" + id);
	
	            if (!TextUtils.isEmpty(selection)) {
	                modSelection.append(" AND " + selection);
	            }
	
	            rowsAffected = sqlDB.update(TUsuario.NOMBRE_TABLA,
	                    values, modSelection.toString(), null);
	            break;
	        case TUsuario.SELECT_ALL:
	            rowsAffected = sqlDB.update(TUsuario.NOMBRE_TABLA,
	                    values, selection, selectionArgs);
	            break;
	            //Configuracion
	        case TConfiguracion.SELECT_DISTINCT:
	            id = uri.getLastPathSegment();
	            modSelection =
	            		new StringBuilder(TConfiguracion.COL_ROWID + "=" + id);

	            if (!TextUtils.isEmpty(selection)) {
	                modSelection.append(" AND " + selection);
	            }

	            rowsAffected = sqlDB.update(TConfiguracion.NOMBRE_TABLA,
	                    values, modSelection.toString(), null);
	            break;
	        case TConfiguracion.SELECT_ALL:
	            rowsAffected = sqlDB.update(TConfiguracion.NOMBRE_TABLA,
	                    values, selection, selectionArgs);
	            break;
	        //Cliente
			case TCliente.SELECT_DISTINCT:
				id = uri.getLastPathSegment();
				modSelection =
						new StringBuilder(TCliente.COL_ROWID + "=" + id);

				if (!TextUtils.isEmpty(selection)) {
					modSelection.append(" AND " + selection);
				}

				rowsAffected = sqlDB.update(TCliente.NOMBRE_TABLA,
						values, modSelection.toString(), null);
				break;
			case TCliente.SELECT_ALL:
				rowsAffected = sqlDB.update(TCliente.NOMBRE_TABLA,
						values, selection, selectionArgs);
				break;
            //Producto
			case TProducto.SELECT_DISTINCT:
				id = uri.getLastPathSegment();
				modSelection =
						new StringBuilder(TProducto.COL_ROWID + "=" + id);

				if (!TextUtils.isEmpty(selection)) {
					modSelection.append(" AND " + selection);
				}

				rowsAffected = sqlDB.update(TProducto.NOMBRE_TABLA,
						values, modSelection.toString(), null);
				break;
			case TProducto.SELECT_ALL:
				rowsAffected = sqlDB.update(TProducto.NOMBRE_TABLA,
						values, selection, selectionArgs);
				break;
			//Pedido
			case TPedidoCab.SELECT_DISTINCT:
				id = uri.getLastPathSegment();
				modSelection =
						new StringBuilder(TPedidoCab.COL_ROWID + "=" + id);

				if (!TextUtils.isEmpty(selection)) {
					modSelection.append(" AND " + selection);
				}

				rowsAffected = sqlDB.update(TPedidoCab.NOMBRE_TABLA,
						values, modSelection.toString(), null);
				break;
			case TPedidoCab.SELECT_ALL:
				rowsAffected = sqlDB.update(TPedidoCab.NOMBRE_TABLA,
						values, selection, selectionArgs);
				break;
			//Pedido
			case TPedidoDet.SELECT_DISTINCT:
				id = uri.getLastPathSegment();
				modSelection =
						new StringBuilder(TPedidoDet.COL_ROWID + "=" + id);

				if (!TextUtils.isEmpty(selection)) {
					modSelection.append(" AND " + selection);
				}

				rowsAffected = sqlDB.update(TPedidoDet.NOMBRE_TABLA,
						values, modSelection.toString(), null);
				break;
			case TPedidoDet.SELECT_ALL:
				rowsAffected = sqlDB.update(TPedidoDet.NOMBRE_TABLA,
						values, selection, selectionArgs);
				break;
	        default:
	            throw new IllegalArgumentException("Unknown URI");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
	}

	public String getType(Uri uri, boolean tabla) {
		int uriType = sURIMatcher.match(uri);
        switch (uriType) {
	      //Usuario
	        case TUsuario.SELECT_ALL:
	            return TUsuario.NOMBRE_TABLA;
	        case TUsuario.SELECT_DISTINCT:
	            return TUsuario.NOMBRE_TABLA;
	          //Configuracion
	        case TConfiguracion.SELECT_ALL:
	            return TConfiguracion.NOMBRE_TABLA;
	        case TConfiguracion.SELECT_DISTINCT:
	            return TConfiguracion.NOMBRE_TABLA;
			//Cliente
			case TCliente.SELECT_ALL:
				return TCliente.NOMBRE_TABLA;
			case TCliente.SELECT_DISTINCT:
				return TCliente.NOMBRE_TABLA;
			//Producto
			case TProducto.SELECT_ALL:
				return TProducto.NOMBRE_TABLA;
			case TProducto.SELECT_DISTINCT:
				return TProducto.NOMBRE_TABLA;
			//Producto
			case TPedidoCab.SELECT_ALL:
				return TPedidoCab.NOMBRE_TABLA;
			case TPedidoCab.SELECT_DISTINCT:
				return TPedidoCab.NOMBRE_TABLA;
			//Producto
			case TPedidoDet.SELECT_ALL:
				return TPedidoDet.NOMBRE_TABLA;
			case TPedidoDet.SELECT_DISTINCT:
				return TPedidoDet.NOMBRE_TABLA;
	        default:
	            return null;
	        }
        }
	
}
