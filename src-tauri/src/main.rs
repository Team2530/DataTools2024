// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]
use data_parser::{self, LogHeader, parse_logfile};
use std::path::{PathBuf};

use tauri::{Result, CustomMenuItem, Menu, Manager, Window, Submenu,};
use tauri::api::dialog;
use serde::Serialize;

#[derive(Clone, Serialize)]
pub struct FileInfo {
    pub file_path: PathBuf,
    pub commit_list: Vec<String>,
}

fn open_file(file_path: &PathBuf, window: &Window) -> Result<()> {
    window.set_title(file_path.file_name().unwrap().to_str().unwrap())?;

    Ok(())
}

fn main() {
    let select = CustomMenuItem::new("selectFile".to_string(), "Select File");
    let file_menu = Submenu::new("File", Menu::new().add_item(select));
    let menu = Menu::new().add_submenu(file_menu);
    
    tauri::Builder::default()
        .menu(menu)
        .on_menu_event(|event| {
            let window = event.window();
            let window_name = window.label().to_string();
            let app: tauri::AppHandle = window.app_handle();

            match event.menu_item_id() {
                "selectFile" => {
                    dialog::FileDialogBuilder::default()
                        .pick_file(move |path_buf| match path_buf {
                            Some(p) => {
                                app.fs_scope().allow_file(&p).expect("Wanted to add the file to scope.");
                                open_file(&p, &app.windows()[window_name.as_str()]).expect("Could not open the file.");
                            }
                            _ => {},
                        });
                }
                _ => {},
            }
        })
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
