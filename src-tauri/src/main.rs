// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]
use data_parser::{self, LogHeader};

// Learn more about Tauri commands at https://tauri.app/v1/guides/features/command
#[tauri::command]
fn file_blaster(file_path: &str) -> Result<LogHeader, String> {
    match data_parser::parse_logfile(file_path) {
        Ok(header) => Ok(header),
        Err(e) => Err(e.to_string())
    }
}

fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![file_blaster])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
