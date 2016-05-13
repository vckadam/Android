/* Copyright 2016 Viplav Kadam
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* Purpose: An iOS app for video playback by download to local file, and also add movie info from IMDB API.
*
* This can be used by
* @Professor Tim Lindquist
* @University Arizona State University
* @author Viplav Kadam mailto: vckadam@asu.edu
* @version 26 April 2016
*/
import Foundation

public class MovieDescription{
    
    var title: String
    
    var year: String
    
    var rated: String
    
    var released:String
    
    var runTime:String
    
    var genre:String
    
    var actors:String
    
    var plot:String
    
    var filename:String
    
    init (jsonStr: NSDictionary){
        
        self.title = ""
        
        self.year=""
        
        self.rated=""
        
        self.released=""
        
        self.runTime=""
        
        self.genre=""
        
        self.actors=""
        
        self.plot=""
        
        self.filename=""
        
        
        if(jsonStr.count > 0){
            
            self.title = (jsonStr["Title"] as? String)!
            
            self.year = (jsonStr["Year"] as? String)!
            
            self.rated = (jsonStr["Rated"] as? String)!
            
            self.released = (jsonStr["Released"] as? String)!
            
            self.runTime = (jsonStr["Runtime"] as? String)!
            
            self.genre = (jsonStr["Genre"] as? String)!
            
            self.actors = (jsonStr["Actors"] as? String)!
            
            self.plot = (jsonStr["Plot"] as? String)!
            
            self.filename = (jsonStr["Filename"] as? String)!
        }
    }
}
