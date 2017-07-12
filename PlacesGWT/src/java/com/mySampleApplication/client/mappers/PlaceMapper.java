package com.mySampleApplication.client.mappers;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.mySampleApplication.shared.model.pojos.PlacePOJO;

import java.util.List;

/**
 * Created by bogdan on 7/11/17.
 */
public interface PlaceMapper extends ObjectMapper<List<PlacePOJO>> {

}
