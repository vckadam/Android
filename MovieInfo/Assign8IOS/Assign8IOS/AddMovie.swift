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

import UIKit
import CoreData

class AddMovie: UIViewController, UISearchBarDelegate {
    
    @IBOutlet weak var movieTitle: UITextField!
    
    @IBOutlet weak var movieYear: UITextField!
    
    @IBOutlet weak var movieRuntime: UITextField!
    
    @IBOutlet weak var movieGenre: UITextField!
    
    @IBOutlet weak var movieActors: UITextField!
    
    @IBOutlet weak var movieRated: UITextField!
    
    @IBOutlet weak var movieReleased: UITextField!
    
    @IBOutlet weak var moviePlot: UITextField!
    
    var searchController:UISearchController!
    
    var movieJson = [String:String]()
    
    var neMovie: MovieDescription?
    
    var filename:String = "NA"
    
    var selectedMovie:NSDictionary?
    
    var selectedTitle:String?
    
    var host:String?
    
    var server_port:String?
    
    var urlString:String?
    
    
    func textViewDidChange(textView: UITextView) {
        
        textView.text = nil
        
        textView.textColor = UIColor.blackColor()
        
    }
    override func touchesBegan(touches: Set<UITouch>, withEvent event: UIEvent?) {
       
        self.movieTitle.resignFirstResponder()
        
        self.movieReleased.resignFirstResponder()
        
        self.movieYear.resignFirstResponder()
        
        self.movieRated.resignFirstResponder()
        
        self.movieRuntime.resignFirstResponder()
        
        self.moviePlot.resignFirstResponder()
        
        self.movieActors.resignFirstResponder()
        
        self.movieGenre.resignFirstResponder()
        
        self.movieRated.resignFirstResponder()
        
        
    }
    @IBAction func clicked(sender: AnyObject) {
        
        movieJson = ["Title":movieTitle.text! as String,"Year":movieYear.text! as String, "Runtime":movieRuntime.text! as String,"Released":movieReleased.text! as String,"Actors":movieActors.text! as String,"Plot":moviePlot.text! as String,"Genre":movieGenre.text! as String,"Rated":movieRated.text! as String,"Filename":filename]
        
        neMovie = MovieDescription.init(jsonStr:movieJson)
        
    }
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        
        self.searchController = UISearchController(searchResultsController:  nil)
        
        self.searchController.searchBar.delegate = self
        
        self.searchController.hidesNavigationBarDuringPresentation = false
        
        self.searchController.dimsBackgroundDuringPresentation = false
        
        self.navigationItem.titleView = searchController.searchBar
        
        
    }
    
    func updateSearchResultsForSearchController(searchController: UISearchController) {
        
    }
    
    func searchBarSearchButtonClicked( searchBar: UISearchBar)
    {
        
        self.movieYear.text = nil
        
        self.movieRuntime.text = nil
        
        self.movieGenre.text = nil
        
        self.movieActors.text = nil
        
        self.movieRated.text = nil
        
        self.movieReleased.text = nil
        
        self.moviePlot.text = nil
        
        
        if let path = NSBundle.mainBundle().pathForResource("Server", ofType: "plist"){
            
            if let dict = NSDictionary(contentsOfFile: path) as? [String:AnyObject] {
                
                host = dict["host"] as? String
                
                server_port = dict["server_port"] as? String
                
            }
            self.urlString = "http://\(host!):\(server_port!)/"
            
            NSLog("viewDidLoad using url: \(urlString)")
            
            
        }
       
        
        let aConnect:StudentCollectionStub = StudentCollectionStub(urlString: self.urlString!)
        
        let resultNames:Bool = aConnect.get(searchBar.text! as String, callback: { (res: String, err: String?) -> Void in
            if err != nil {
                
                NSLog(err!)
                
            }else{
                
                if let data: NSData = res.dataUsingEncoding(NSUTF8StringEncoding){
                    
                    do{
                        let dict = try NSJSONSerialization.JSONObjectWithData(data,options:.MutableContainers) as?[String:AnyObject]
                        
                        self.selectedMovie = (dict!["result"] as? NSDictionary)
                        
                        self.selectedTitle = self.selectedMovie!["Title"] as? String
                        
                        
                        if(self.selectedTitle! == "Unknown") {
                            
                            self.searchOnOMDB(searchBar.text! as String)
                        }
                        else {
                            
                            self.movieTitle.text = self.selectedTitle!
                            
                            self.movieYear.text = (self.selectedMovie!["Year"] as? String)
                            
                            self.movieRuntime.text = (self.selectedMovie!["Runtime"] as? String)
                            
                            self.movieGenre.text = (self.selectedMovie!["Genre"] as? String)
                            
                            self.movieActors.text = (self.selectedMovie!["Actors"] as? String)
                            
                            self.movieRated.text = (self.selectedMovie!["Rated"] as? String)
                            
                            self.movieReleased.text =  (self.selectedMovie!["Released"] as? String)
                            
                            self.moviePlot.text = (self.selectedMovie!["Plot"] as? String)
                            
                            self.filename = (self.selectedMovie!["Filename"] as? String)!
                        }
                        
                        self.searchController.dimsBackgroundDuringPresentation = false
                        
                        searchBar.resignFirstResponder()
                        
                    } catch {
                        
                        NSLog("unable to convert to dictionary")
                        
                    }
                }
            }
        })
       
    }
    func searchOnOMDB(str:String) {
        
        var newString:String =  "http://www.omdbapi.com/?t="
        newString+=str+"&y=&plot=short&r=json"
        
        let urlString:String = newString.stringByReplacingOccurrencesOfString(" ", withString: "+")
        
        let aConnect:StudentCollectionStub = StudentCollectionStub(urlString: urlString)
        
        let resultNames:Bool = aConnect.get(str as String, callback: { (res: String, err: String?) -> Void in
            
            if err != nil {
                
                NSLog(err!)
                
            }else{
                
                
                if let data: NSData = res.dataUsingEncoding(NSUTF8StringEncoding){
                    
                    do{
                        
                        let dict = try NSJSONSerialization.JSONObjectWithData(data,options:.MutableContainers) as?[String:AnyObject]
                        
                        self.movieTitle.text = (dict!["Title"] as? String)
                        
                        self.movieYear.text = (dict!["Year"] as? String)
                        
                        self.movieRuntime.text = (dict!["Runtime"] as? String)
                        
                        self.movieGenre.text = (dict!["Genre"] as? String)
                        
                        self.movieActors.text = (dict!["Actors"] as? String)
                        
                        self.movieRated.text = (dict!["Rated"] as? String)
                        
                        self.movieReleased.text = (dict!["Released"] as? String)
                        
                        self.moviePlot.text = (dict!["Plot"] as? String)
                        
                        
                    } catch {
                        
                        NSLog("unable to convert to dictionary")
                        
                    }
                }
            }
        })
        
    }
    func searchBarTextDidEndEditing(searchBar: UISearchBar) {
        
        searchBar.resignFirstResponder()
        
    }
    
    override func didReceiveMemoryWarning() {
        
        super.didReceiveMemoryWarning()
        
        
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        
        
        if(segue.identifier == "exit"){
            
            let destinationViewController =  segue.destinationViewController as! ViewController
            
            destinationViewController.addMovie = self.neMovie
            
        }
        
    }
}
