$output = "project_code.txt"
$root = (Get-Location).Path

if (Test-Path $output) {
    Remove-Item $output
}

$extensions = @(
    "*.kt",
    "*.kts",
    "*.xml",
    "*.gradle",
    "*.properties",
    "*.json",
    "*.toml"
)

$excludeDirs = @(
    "\build\",
    "\.gradle\",
    "\.idea\",
    "\generated\",
    "\captures\"
)

$excludeFiles = @(
    "local.properties",
    "google-services.json",
    "secrets.properties",
    "project_code.txt"
)

Get-ChildItem -Recurse -File -Include $extensions | Where-Object {
    $path = $_.FullName
    $name = $_.Name

    $shouldExclude = $false

    foreach ($dir in $excludeDirs) {
        if ($path.Contains($dir)) {
            $shouldExclude = $true
            break
        }
    }

    if ($excludeFiles -contains $name) {
        $shouldExclude = $true
    }

    -not $shouldExclude
} | Sort-Object FullName | ForEach-Object {
    $relativePath = $_.FullName.Replace($root + "\", "")

    Add-Content -Path $output -Value "`n`n=============================="
    Add-Content -Path $output -Value "FILE: $relativePath"
    Add-Content -Path $output -Value "==============================`n"
    Get-Content -Path $_.FullName | Add-Content -Path $output
}

Write-Host "Done. Code saved to $output"