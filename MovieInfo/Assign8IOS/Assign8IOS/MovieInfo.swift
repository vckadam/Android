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
import UIKit
import CoreData
class MovieInfo: UIViewController,UIPickerViewDataSource,UIPickerViewDelegate
 {
    
    
    @IBOutlet weak var playButton: UINavigationBar!
    
    @IBOutlet weak var pickerview: UIPickerView!
    
    @IBOutlet weak var labeltitle: UILabel!
    
    @IBOutlet weak var labelreleased: UILabel!
    
    @IBOutlet weak var labelyear: UILabel!
    
    @IBOutlet weak var labelplot: UILabel!
    
    @IBOutlet weak var textViewActors: UITextView!
    
    @IBOutlet weak var labelgenre: UILabel!
    
    @IBOutlet weak var textViewPlot: UITextView!
    
    @IBOutlet weak var labelrated: UILabel!
    
    
    @IBOutlet weak var labelrun: UILabel!
    
    var selectedMovie : String = ""
    
    
    var genreTypes = [String]()
    
    var filename:String="NA"
    
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        
        let value = UIInterfaceOrientation.Portrait.rawValue
        
        UIDevice.currentDevice().setValue(value, forKey:"orientation")
        
        self.navigationController?.navigationBarHidden = false
        
        pickerview.dataSource = self
        
        pickerview.delegate = self

        self.labeltitle.text = selectedMovie
        
        let appDelegate = UIApplication.sharedApplication().delegate as! AppDelegate
        
        let managedContext = appDelegate.managedObjectContext
        
        let selectRequest = NSFetchRequest(entityName: "Movie")
        
        selectRequest.predicate = NSPredicate(format: "movieTitle == %@",selectedMovie)
        
        do{
            let results = try managedContext.executeFetchRequest(selectRequest)
        
           
            self.labelyear.text = results[0].valueForKey("movieYear") as? String
            
            self.labelreleased.text = results[0].valueForKey("movieReleased") as? String
            
            self.labelrated.text = results[0].valueForKey("movieRated") as? String
            
            self.labelrun.text = results[0].valueForKey("movieRuntime") as? String
            
            self.textViewPlot.text = results[0].valueForKey("moviePlot") as? String
            
            self.textViewActors.text = results[0].valueForKey("movieActors") as? String
            
            self.filename = (results[0].valueForKey("movieFileName") as? String)!
            
            if(filename == "NA") {
                
                playButton.hidden = true
                
            }
                
            else {
                
                playButton.hidden = false
                
            }
            
        }catch let error as NSError{
            
            NSLog("Error: \(error)")
            
        }
        
        self.updateTakesPicker()
        
    }
    func updateTakesPicker(){
        
        let appDelegate = UIApplication.sharedApplication().delegate as! AppDelegate
        
        let managedContext = appDelegate.managedObjectContext
        
        let selectRequest = NSFetchRequest(entityName: "Movie")
        
        selectRequest.predicate = NSPredicate(format: "movieTitle == %@",selectedMovie)
        
        do{
            
            let results = try managedContext.executeFetchRequest(selectRequest)
            
            if results.count > 0 {
                
                let types = results[0].mutableSetValueForKey("genre_type")
                
                for type in types {
                    
                    let crsName:String = (type.valueForKey("genretype") as? String)!
                    
                    genreTypes.append(crsName)
                }
            }
            
        } catch let error as NSError{
            
            NSLog("Error: \(error)")
            
        }
        self.pickerview.reloadAllComponents()
        
    }

    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        
        return genreTypes.count
        
    }
    
    func pickerView(pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        return genreTypes[row]
        
    }
    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
        
        return 1
        
    }
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        
        if(segue.identifier == "video") {
            
            let destinationViewController = segue.destinationViewController as? VideoPlayer
            
            destinationViewController?.filename = self.filename
            
            
        }
        
        
    }

    
    override func didReceiveMemoryWarning() {
        
        super.didReceiveMemoryWarning()
        
        
    }
    
}