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
class ViewController: UITableViewController{
    var names = [String]()
    
    var movies = [NSManagedObject]()
   
    var movieJson = [String:String]()
    var neMovie: MovieDescription?
    var addMovie: MovieDescription?
    
    var selectedMovie : String = ""
    
    @IBOutlet var tableview: UITableView!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationItem.leftBarButtonItem = self.editButtonItem()
        
        let appDelegate = UIApplication.sharedApplication().delegate as! AppDelegate
        
        let managedContext = appDelegate.managedObjectContext
        
        
        let fetchRequest = NSFetchRequest(entityName: "Movie")
        
        
        do {
            let results =
            try managedContext.executeFetchRequest(fetchRequest)
            
            movies = results as! [NSManagedObject]
            
            var i = 0;
            
            names.removeAll()
            
            while(i < movies.count) {
                let res = movies[i] as NSManagedObject
                
                names.append(res.valueForKey("movieTitle") as! String)
                
                i++
            }
            
        } catch let error as NSError {
            
            print("Could not fetch \(error), \(error.userInfo)")
        }
        
        tableview.registerClass(UITableViewCell.self, forCellReuseIdentifier: "Cell")
        
    }
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
            
            let s:String = self.names[indexPath.row]
            NSLog(s)
            
            self.names.removeAtIndex(indexPath.row)
           
            let appDelegate =
            UIApplication.sharedApplication().delegate as! AppDelegate
            
            let managedContext = appDelegate.managedObjectContext
            
            managedContext.deleteObject(self.movies[indexPath.row] as NSManagedObject)
            
            movies.removeAtIndex(indexPath.row)
            
            do {
                try managedContext.save()
                
            } catch {
                
                fatalError("Failure to save context: \(error)")
            }
            
            tableView.beginUpdates()
            
            self.tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
            
            tableView.endUpdates()
            
        } else if editingStyle == .Insert {
        }
    }
    
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        
        return true
    }
    
    override func didReceiveMemoryWarning() {
        
        super.didReceiveMemoryWarning()
        
    }
    
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.names.count
    }
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("Cell")
        
        cell!.textLabel!.text = names[indexPath.row]
        
        return cell!
    }
    
    override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        
        self.selectedMovie = self.names[indexPath.row]
        
        performSegueWithIdentifier("Details", sender: self)
        
        self.tableview.deselectRowAtIndexPath(indexPath, animated: true)
        
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        
        if(segue.identifier == "Details"){
            
            let destinationViewController =  segue.destinationViewController as! MovieInfo
            
            destinationViewController.selectedMovie = self.selectedMovie
           
        }
        
    }
    
    @IBAction func saveNewMovie(segue: UIStoryboardSegue)    {
        
        NSLog("in the add")
       
        let appDelegate = UIApplication.sharedApplication().delegate as! AppDelegate
        
        let managedContext = appDelegate.managedObjectContext
        
        
        let entity =  NSEntityDescription.entityForName("Movie",
            inManagedObjectContext:managedContext)
        
        let movie = NSManagedObject(entity: entity!,
            insertIntoManagedObjectContext: managedContext)
        
       
        movie.setValue(addMovie!.title , forKey: "movieTitle")
        
        movie.setValue(addMovie!.year, forKey: "movieYear")
        
        movie.setValue(addMovie!.runTime, forKey: "movieRuntime")
        
        movie.setValue(addMovie!.actors, forKey: "movieActors")
        
        movie.setValue(addMovie!.rated, forKey: "movieRated")
        
        movie.setValue(addMovie!.released, forKey: "movieReleased")
        
        movie.setValue(addMovie!.plot, forKey: "moviePlot")
        
        movie.setValue(addMovie!.filename, forKey:"movieFileName")
        
        var course:NSManagedObject?
        
        let a:String = addMovie!.genre
        
        let fullNameArr = a.characters.split{$0 == ","}.map(String.init)
        
        var i = 0
        
        while(i < fullNameArr.count) {
            
            do{
                let entity = NSEntityDescription.entityForName("Genre_Type", inManagedObjectContext: managedContext)
                
                course = NSManagedObject(entity: entity!, insertIntoManagedObjectContext: managedContext)
                
                course!.setValue(fullNameArr[i],forKey:"genretype")
                
                try managedContext.save()
                    
               
            }catch let error as NSError{
                
                NSLog(" Error is \(error)")
                
            }
            
            
            let selectStudRequest = NSFetchRequest(entityName: "Movie")
            
            selectStudRequest.predicate = NSPredicate(format: "movieTitle == %@",addMovie!.title)
            
            do{
                
                let resultStud = try managedContext.executeFetchRequest(selectStudRequest)
                
                if resultStud.count > 0 {
                    
                    (resultStud[0].mutableSetValueForKey("genre_type")).addObject(course!)
                    
                    try managedContext.save()
                }
                do{
                    
                    try managedContext.save()
                    
                } catch let error as NSError{
                    
                    NSLog(" \(error)")
                    
                }
            }catch let error as NSError{
                
                NSLog("Error is \(error)")
                
            }
            i++
        }
        
        do {
            try managedContext.save()
            
            movies.append(movie)
            
        } catch let error as NSError  {
            
            print("Could not save \(error), \(error.userInfo)")
            
        }
        
        
	
        self.viewDidLoad()
        
        self.tableview.reloadData()
        
    }

    
}

