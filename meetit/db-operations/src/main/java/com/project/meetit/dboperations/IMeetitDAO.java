package com.project.meetit.dboperations;

import com.project.meetit.dboperations.model.GenericModel;

import java.util.List;

public interface IMeetitDAO {

    default boolean create(GenericModel genericModel) {
        return false;
    }

    default boolean update(int id, GenericModel genericModel) {
        return false;
    }

    default boolean delete(int id) {
        return false;
    }

    default List<GenericModel> list() {
        return null;
    }
}
